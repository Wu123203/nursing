import axios from 'axios';
axios.defaults.withCredentials = true;

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
});

service.interceptors.request.use(
    config => {
        // 从 localStorage 获取 Token
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
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response.data;
        } else if(response.status === 201){
            return response;
        } else {
            return Promise.reject(response);
        }
    },
    error => {
        console.error("请求错误:", error);
        // 如果有响应数据，尝试提取错误信息
        if (error.response) {
            const { status, data } = error.response;
            console.error(`HTTP状态码: ${status}`);
            console.error("响应数据:", data);
            // 返回响应数据以便前端处理
            return Promise.resolve(data || { code: status, msg: '请求失败' });
        }
        return Promise.reject(error);
    }
);

export default service;
