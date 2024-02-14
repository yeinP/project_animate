import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import Review from "@/pages/Review.vue";
import {createRouter, createWebHistory} from "vue-router";
import ShelterInfo from "@/pages/ShelterInfo.vue";


const routes = [
    {path:'/', component: Home},
    {path:'/login', component: Login },
    {path: '/review', component: Review},
    {path: '/sheltersInfo', component: ShelterInfo}

]
const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;
