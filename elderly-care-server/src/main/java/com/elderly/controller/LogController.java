package com.elderly.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.elderly.common.vo.JSONReturn;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 日志管理控制器
 * 提供运行时日志查看和动态级别调整
 */
@RestController
@RequestMapping("/admin/logs")
@PreAuthorize("hasRole('ADMIN')")
public class LogController {

    private static final String LOG_DIR = "./logs";

    /**
     * 查看最近 N 行日志
     * GET /admin/logs/view?file=elderly-care&lines=100
     */
    @GetMapping("/view")
    public JSONReturn<?> viewLogs(@RequestParam(defaultValue = "elderly-care") String file,
                                  @RequestParam(defaultValue = "100") int lines) {
        try {
            Path logPath = Paths.get(LOG_DIR, file + ".log");
            if (!Files.exists(logPath)) {
                return JSONReturn.failed("日志文件不存在: " + logPath);
            }

            List<String> allLines = Files.readAllLines(logPath);
            int from = Math.max(0, allLines.size() - lines);
            List<String> recentLines = allLines.subList(from, allLines.size());

            Map<String, Object> result = new HashMap<>();
            result.put("file", file + ".log");
            result.put("totalLines", allLines.size());
            result.put("shownLines", recentLines.size());
            result.put("content", String.join("\n", recentLines));

            return JSONReturn.success(result);
        } catch (IOException e) {
            return JSONReturn.failed("读取日志失败: " + e.getMessage());
        }
    }

    /**
     * 列出所有日志文件及大小
     * GET /admin/logs/files
     */
    @GetMapping("/files")
    public JSONReturn<?> listLogFiles() {
        try {
            Path logDir = Paths.get(LOG_DIR);
            if (!Files.exists(logDir)) {
                return JSONReturn.success(Collections.emptyList());
            }

            try (Stream<Path> stream = Files.list(logDir)) {
                List<Map<String, Object>> files = stream
                        .filter(Files::isRegularFile)
                        .sorted((a, b) -> {
                            try {
                                return Long.compare(Files.getLastModifiedTime(b).toMillis(),
                                        Files.getLastModifiedTime(a).toMillis());
                            } catch (IOException e) {
                                return 0;
                            }
                        })
                        .map(p -> {
                            Map<String, Object> info = new HashMap<>();
                            info.put("name", p.getFileName().toString());
                            try {
                                info.put("size", Files.size(p));
                                info.put("sizeFormatted", formatSize(Files.size(p)));
                                info.put("lastModified", Files.getLastModifiedTime(p).toString());
                            } catch (IOException ignored) {}
                            return info;
                        })
                        .collect(Collectors.toList());

                return JSONReturn.success(files);
            }
        } catch (IOException e) {
            return JSONReturn.failed("列出日志文件失败: " + e.getMessage());
        }
    }

    /**
     * 查看当前所有 Logger 及级别
     * GET /admin/logs/levels
     */
    @GetMapping("/levels")
    public JSONReturn<?> getLogLevels() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Map<String, String>> loggers = context.getLoggerList().stream()
                .filter(l -> l.getLevel() != null)
                .map(l -> {
                    Map<String, String> info = new HashMap<>();
                    info.put("name", l.getName());
                    info.put("level", l.getLevel().toString());
                    return info;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("rootLevel", context.getLogger("ROOT").getLevel().toString());
        result.put("loggers", loggers);
        return JSONReturn.success(result);
    }

    /**
     * 动态修改 Logger 级别（临时生效，重启恢复）
     * PUT /admin/logs/levels?name=com.elderly&level=DEBUG
     */
    @PutMapping("/levels")
    public JSONReturn<?> setLogLevel(@RequestParam String name,
                                     @RequestParam String level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger;

        if ("ROOT".equalsIgnoreCase(name)) {
            logger = context.getLogger("ROOT");
        } else {
            logger = context.getLogger(name);
        }

        Level newLevel = Level.toLevel(level, null);
        if (newLevel == null && !"null".equalsIgnoreCase(level)) {
            return JSONReturn.failed("无效的日志级别: " + level + "，支持: TRACE, DEBUG, INFO, WARN, ERROR, OFF");
        }

        logger.setLevel(newLevel);

        Map<String, String> result = new HashMap<>();
        result.put("logger", name);
        result.put("level", newLevel == null ? "继承父级" : newLevel.toString());
        return JSONReturn.success("日志级别已更新", result);
    }

    /**
     * 删除旧归档日志
     * DELETE /admin/logs/clean?beforeDays=7
     */
    @DeleteMapping("/clean")
    public JSONReturn<?> cleanOldLogs(@RequestParam(defaultValue = "7") int beforeDays) {
        try {
            long cutoff = System.currentTimeMillis() - (long) beforeDays * 24 * 60 * 60 * 1000;
            Path logDir = Paths.get(LOG_DIR);
            if (!Files.exists(logDir)) {
                return JSONReturn.success("无日志需要清理");
            }

            int count = 0;
            try (Stream<Path> stream = Files.list(logDir)) {
                List<Path> toDelete = stream
                        .filter(Files::isRegularFile)
                        .filter(p -> p.getFileName().toString().endsWith(".gz"))
                        .filter(p -> {
                            try {
                                return Files.getLastModifiedTime(p).toMillis() < cutoff;
                            } catch (IOException e) {
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

                for (Path path : toDelete) {
                    Files.delete(path);
                    count++;
                }
            }

            return JSONReturn.success("已清理 " + count + " 个归档日志文件");
        } catch (IOException e) {
            return JSONReturn.failed("清理日志失败: " + e.getMessage());
        }
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
    }
}
