import { createApp } from 'vue'
import store from "@/scrpits/store";
import App from './App.vue'
import router from "@/scrpits/router";


createApp(App).use(store).use(router).mount('#app')
