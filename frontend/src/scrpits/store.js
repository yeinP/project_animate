import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
    state () {
        return {
            user:{
                userNo:0
            }
        }
    },
    mutations: {
        setUser(state, payload) {
            state.user.userNo = payload
        }
    }
})

export default store;