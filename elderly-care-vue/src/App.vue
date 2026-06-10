<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
@import "./assets/css/index.css";

/* 全局表格优化样式 */
.el-table {
  font-size: 14px;
  color: #2c3e50;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
}

.el-table th {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
  font-weight: 600;
  text-align: center;
  padding: 16px 0;
  border: none !important;
}

.el-table th::before {
  display: none;
}

.el-table td {
  text-align: center;
  padding: 14px 0;
  border-bottom: 1px solid #e8e8e8 !important;
  border-right: 1px solid #e8e8e8 !important;
  border-top: none !important;
  border-left: none !important;
}

.el-table .el-table__cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
}

.el-table .cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-left: 16px;
  padding-right: 16px;
  color: #2c3e50;
}

.el-table tbody tr {
  transition: all 0.3s ease;
}

.el-table tbody tr:hover > td {
  background-color: #f0f5ff !important;
}

.el-table tbody tr:hover > th {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%) !important;
}

.el-table--border .el-table__cell {
  border-right: 1px solid #e8e8e8;
}

.el-table--border th.el-table__cell {
  border-bottom: none;
}

.el-table--border td:first-child .cell {
  padding-left: 20px;
}

.el-table .el-table__header-wrapper th {
  height: 56px;
  line-height: 56px;
}

.el-table .el-table__body-wrapper td {
  height: 52px;
  line-height: 52px;
}

.el-table .el-dropdown {
  vertical-align: middle;
}

.el-table .el-dropdown-link {
  cursor: pointer;
  color: #667eea;
  font-weight: 500;
}

.el-table .el-dropdown-link:hover {
  color: #764ba2;
}

.el-table .operation-cell {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.el-table .el-button--small {
  padding: 6px 12px;
  font-size: 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.el-table .el-button--small:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.el-table .el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.el-table .el-button--primary:hover {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%);
}

.el-table .el-button--danger {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
  border: none;
}

.el-table .el-button--danger:hover {
  background: linear-gradient(135deg, #ff5252 0%, #ee4444 100%);
}

.el-table .el-tag {
  margin-right: 8px;
  border-radius: 4px;
  border: none;
}

.el-table .el-link {
  margin-right: 12px;
  font-weight: 500;
}

.el-table .el-link:last-child {
  margin-right: 0;
}

.el-table .el-link--primary {
  color: #667eea;
}

.el-table .el-link--primary:hover {
  color: #764ba2;
}

.el-table .el-link--danger:hover {
  color: #ee5a5a;
}

.el-pagination {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
  padding: 16px 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.06);
  padding-left: 20px;
  padding-right: 20px;
}

.el-pagination .el-pagination__total {
  margin-right: 16px;
  color: #666;
}

.el-pagination .el-pagination__sizes {
  margin-right: 12px;
}

.el-pagination .el-pagination__jump {
  margin-left: 16px;
}

.el-pagination .el-pager li {
  border-radius: 6px;
  margin: 0 4px;
  transition: all 0.3s ease;
}

.el-pagination .el-pager li:hover {
  color: #667eea;
}

.el-pagination .el-pager li.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.el-pagination .el-pager li.active:hover {
  color: #fff;
}

.el-pagination .btn-prev,
.el-pagination .btn-next {
  border-radius: 6px;
}

.el-pagination .btn-prev:hover,
.el-pagination .btn-next:hover {
  color: #667eea;
}

/* 全局表单优化样式 */
.el-input__wrapper {
  border-radius: 8px;
  padding: 4px 11px;
  transition: all 0.3s ease;
}

.el-input__wrapper:hover {
  box-shadow: 0 0 0 1px #667eea inset;
}

.el-input__wrapper.is-focus {
  box-shadow: 0 0 0 1px #667eea inset;
}

.el-input__inner {
  color: #2c3e50;
}

.el-input__prefix {
  color: #667eea;
}

.el-textarea__inner {
  border-radius: 8px;
  padding: 12px;
  transition: all 0.3s ease;
}

.el-textarea__inner:hover {
  box-shadow: 0 0 0 1px #667eea inset;
}

.el-textarea__inner:focus {
  box-shadow: 0 0 0 1px #667eea inset;
}

.el-form-item__label {
  color: #333;
  font-weight: 500;
  font-size: 14px;
}

/* 全局对话框优化样式 */
.el-dialog {
  border-radius: 12px;
  overflow: hidden;
}

.el-dialog__header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px 24px;
  margin: 0;
}

.el-dialog__title {
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.el-dialog__headerbtn .el-dialog__close {
  color: #fff;
  font-size: 20px;
}

.el-dialog__headerbtn:hover .el-dialog__close {
  color: #f0f5ff;
}

.el-dialog__body {
  padding: 24px;
  background: #fff;
}

.el-dialog__footer {
  padding: 16px 24px;
  border-top: 1px solid #f0f0f0;
  background: #fafafa;
}

/* 全局按钮优化样式 */
.el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.el-button--primary:hover {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.el-button--primary.is-disabled {
  background: #c0bfe6;
}

.el-button--danger {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
  border: none;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.el-button--danger:hover {
  background: linear-gradient(135deg, #ff5252 0%, #ee4444 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.4);
}

.el-button--default {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.el-button--default:hover {
  border-color: #667eea;
  color: #667eea;
}

/* 全局搜索框样式 */
.search-box {
  background: #fff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
}

.search-box .el-form-item {
  margin-bottom: 0;
}

.search-box .el-input {
  width: 260px;
}

.search-box .el-button {
  border-radius: 8px;
  padding: 10px 20px;
}

/* 全局容器样式 */
.container {
  background: #fff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
  margin-top: 20px;
}

/* 全局标签样式 */
.el-tag {
  border-radius: 6px;
  border: none;
  font-weight: 500;
}

.el-tag--success {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  color: #fff;
}

.el-tag--warning {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
  color: #fff;
}

.el-tag--danger {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
  color: #fff;
}

.el-tag--info {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  color: #666;
}

/* 全局下拉菜单样式 */
.el-dropdown-menu__item {
  border-radius: 6px;
  margin: 4px 8px;
  transition: all 0.3s ease;
}

.el-dropdown-menu__item:hover {
  background: #f0f5ff;
  color: #667eea;
}

.el-dropdown-menu__item.danger:hover {
  background: #fff2f2;
  color: #ff6b6b;
}

/* 全局消息提示样式 — 暗色渐变背景 + 白色文字，用 !important 确保覆盖 Element Plus CSS 变量 */
.el-message {
  border-radius: 8px !important;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15) !important;
  min-width: 200px !important;
}

/* ----- error: 红底白字 ----- */
.el-message--error {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%) !important;
  border: none !important;
  --el-message-text-color: #fff !important;
  --el-message-bg-color: transparent !important;
}
.el-message--error .el-message__content { color: #fff !important; }
.el-message--error .el-message__icon { color: #fff !important; }
.el-message--error .el-message__closeBtn { color: rgba(255,255,255,0.7) !important; }
.el-message--error .el-message__closeBtn:hover { color: #fff !important; }

/* ----- success: 绿底白字 ----- */
.el-message--success {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%) !important;
  border: none !important;
  --el-message-text-color: #fff !important;
  --el-message-bg-color: transparent !important;
}
.el-message--success .el-message__content { color: #fff !important; }
.el-message--success .el-message__icon { color: #fff !important; }
.el-message--success .el-message__closeBtn { color: rgba(255,255,255,0.7) !important; }
.el-message--success .el-message__closeBtn:hover { color: #fff !important; }

/* ----- warning: 粉黄底深字 ----- */
.el-message--warning {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%) !important;
  border: none !important;
}
.el-message--warning .el-message__content { color: #333 !important; }
.el-message--warning .el-message__icon { color: #333 !important; }
.el-message--warning .el-message__closeBtn { color: rgba(51,51,51,0.6) !important; }
.el-message--warning .el-message__closeBtn:hover { color: #333 !important; }

/* ----- info: 青粉底深字 ----- */
.el-message--info {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%) !important;
  border: none !important;
}
.el-message--info .el-message__content { color: #333 !important; }
.el-message--info .el-message__icon { color: #333 !important; }
.el-message--info .el-message__closeBtn { color: rgba(51,51,51,0.6) !important; }
.el-message--info .el-message__closeBtn:hover { color: #333 !important; }

/* 全局确认框样式 */
.el-message-box {
  border-radius: 12px;
  overflow: hidden;
  padding-bottom: 0;
}

.el-message-box__header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 16px 20px;
}

.el-message-box__title {
  color: #fff;
  font-weight: 600;
}

.el-message-box__headerbtn .el-message-box__close {
  color: #fff;
}

.el-message-box__content {
  padding: 20px 24px;
}

.el-message-box__footer {
  padding: 12px 20px;
  border-top: 1px solid #f0f0f0;
}
</style>
