import axios from 'axios';

axios.defaults.withCredentials = true;

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
});

service.interceptors.request.use(
    config => {
        const userJson = localStorage.getItem("user");
        if (userJson) {
            try {
                const user = JSON.parse(userJson);
                if (user.token) {
                    config.headers.Authorization = 'Bearer ' + user.token;
                }
            } catch (e) {
                console.error("解析用户信息失败:", e);
            }
        }
        return config;
    },
    error => {
        console.error("请求配置错误:", error);
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    response => {
        // 统一返回 data 中的业务数据 —— 后端统一格式 {code, msg, data, timestamp}
        const data = response.data;
        if (data && typeof data === 'object') {
            return data;
        }
        return data;
    },
    error => {
        console.error("请求错误:", error);

        // 有 HTTP 响应（后端返回了错误）
        if (error.response) {
            const { status, data } = error.response;

            // 后端返回了 JSON 格式的错误信息
            if (data && typeof data === 'object' && data.code !== undefined) {
                return data;
            }

            // 没有标准 JSON 响应，根据 HTTP 状态码构造错误信息
            let msg = '请求失败';
            switch (status) {
                case 400: msg = '请求参数错误'; break;
                case 401: msg = '请先登录'; break;
                case 403: msg = '权限不足'; break;
                case 404: msg = '接口不存在'; break;
                case 429: msg = '请求过于频繁，请稍后再试'; break;
                case 500: msg = '服务器内部错误'; break;
                case 502: msg = '网关错误'; break;
                case 503: msg = '服务暂时不可用'; break;
                default:  msg = `请求失败(${status})`;
            }
            return { code: status, msg };
        }

        // 请求超时
        if (error.code === 'ECONNABORTED') {
            return { code: -1, msg: '请求超时，请检查网络连接' };
        }

        // 网络断开等无响应的情况
        return { code: -1, msg: '网络异常，请稍后重试' };
    }
);

export default service;
