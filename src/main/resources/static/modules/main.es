import Vue from 'vue';

// 引入应用程序
import App from './app.vue';
import ElementUI from 'element-ui';
Vue.use(ElementUI)
// 引入样式
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
Vue.prototype.$http = axios;

// 实例化
new Vue({
	// 渲染应用程序
	render: h => h(App)
// 确定挂载点
}).$mount('#app');