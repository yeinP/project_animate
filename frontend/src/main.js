import { createApp } from 'vue'
import store from "@/scrpits/store";
import App from './App.vue'
import router from "@/scrpits/router";
import PortalVue                           from 'portal-vue'


createApp(App).use(PortalVue).use(store).use(router).mount('#app')
