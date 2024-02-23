import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import Review from "@/pages/Review.vue";
import {createRouter, createWebHistory} from "vue-router";
import ShelterInfo from "@/pages/ShelterInfo.vue";
import RegisterStep1 from "@/pages/register/RegisterStep1.vue";
import RegisterStep2 from "@/pages/register/RegisterStep2.vue";
import Adoption from "@/pages/Adoption.vue";
import Miss from "@/pages/miss_report/Miss.vue";



const routes = [
    {path:'/', component: Home},
    {path:'/login', component: Login },
    {path: '/review', component: Review},
    {path: '/sheltersInfo', component: ShelterInfo},
    {path: '/register/step1', component: RegisterStep1},
    {path: '/register/step2', component: RegisterStep2},
    {path: '/animal/miss_care', component: Miss},
    {path: '/adoption', component: Adoption},

]
const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;
