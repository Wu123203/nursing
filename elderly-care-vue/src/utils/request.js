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
            // 如果有响应数据，即使状态码不是200也返回数据
            if (response.data) {
                return response.data;
            }
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
            // 如果后端返回了JSON格式的错误信息，直接返回
            if (data && typeof data === 'object' && (data.code !== undefined || data.msg !== undefined)) {
                return Promise.resolve(data);
            }
            // 否则包装成统一格式
            return Promise.resolve({ 
                code: status, 
                msg: data && data.message ? data.message : '请求失败' 
            });
        }
        // 网络错误等无响应的情况
        return Promise.reject({ code: -1, msg: '网络异常，请稍后重试' });
    }
);

export default service;
