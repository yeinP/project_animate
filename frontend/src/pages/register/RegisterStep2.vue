<template>
  <div class="container">
    <div class="content">
    <main>
      <div class="py-5 text-center">
      </div>
      <div class="g-5">
        <div class="col-md-7 col-lg-8">
          <h4 class="mb-3">회원가입</h4>
          <form ref="form" class="needs-validation" novalidate @submit.prevent="signUpSubmit">
            <div class="row g-3">
              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></span>
                  <input type="text" class="form-control" v-model="userId" placeholder="아이디" required :rules="userIdRules">
                  <div class="invalid-feedback">
                    {{ userIdRules[0](userId) || '' }}
                  </div>
                </div>

              </div>


              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-lock" aria-hidden="true"></i></span>
                  <input type="password" class="form-control" v-model="userPassword" placeholder="비밀번호" required :rules="userPasswordRules">

                  <div class="invalid-feedback">
                    <span v-for="rule in userPasswordRules" :key="rule">{{ rule(userPassword) || '' }}</span>
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-lock" aria-hidden="true"></i></span>
                  <input type="password" class="form-control" v-model="userPasswordCheck" placeholder="비밀번호 확인" :rules="passwordCheckRules.concat(passwordConfirmationRule)" required>

                  <div class="invalid-feedback">
                    {{ passwordCheckRules[0](userPasswordCheck) || '' }}
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-id-card" aria-hidden="true"></i></span>
                  <input type="text" class="form-control" v-model="userName" placeholder="이름" required :rules="userNameRules">
                  <div class="invalid-feedback">
                    {{ userNameRules[0](userName) || '' }}
                  </div>
                </div>
              </div>

              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-user-plus" aria-hidden="true"></i></span>
                  <input type="text" class="form-control" v-model="userNickname" placeholder="닉네임" required :rules="nicknameRules">
                  <div class="invalid-feedback">
                    <span v-for="rule in nicknameRules" :key="rule">{{ rule(userNickname) || '' }}</span>
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa fa-phone" aria-hidden="true"></i></span>
                  <input type="text" class="form-control" v-model="userPhone" placeholder="전화번호" required :rules="userPhoneRules">
                  <div class="invalid-feedback">
                    <span v-for="rule in userPhoneRules" :key="rule">{{ rule(userPhone) || '' }}</span>
                  </div>
                </div>
              </div>

              <div class="col-12">
                <div class="input-group has-validation">
                  <span class="input-group-text"><i class="fa  fa-envelope-o" aria-hidden="true"></i></span>
                  <input type="text" class="form-control" v-model="userEmail" placeholder="[선택] 이메일" :rules="emailRules">
                  <div class="invalid-feedback">
                    {{ emailRules[0](userEmail) || '' }}
                  </div>
                </div>
              </div>
            </div>
            <button class="w-100 btn btn-primary btn-lg"  @click.prevent="signUpSubmit">인증요청</button>
          </form>
        </div>
      </div>
    </main>
  </div>
  </div>
</template>
<script>
export default {
  name:"RegisterStep2",
  data(){
    return {
      userId:"",
      userIdRules:[
        (v) => !!v || '아이디를 작성해주세요',
      ],
      userPassword: "",
      userPasswordRules: [
        (v) => !!v || '비밀번호을 작성해주세요',
        (v) => (v && v.length >= 5) || '비밀번호는 5글자 이상 작성해주세요',
        (v) => /(?=.*\d)/.test(v) || '숫자를 포함해야합니다',
        (v) => /([!@$%])/.test(v) || '특수문자를 포함해야합니다 [!@#$%]'
      ],
      userPasswordCheck : "",
      passwordCheckRules: [
        (v) => !!v || '비밀번호를 확인을 작성해주세요',
      ],
      userName:"",
      userNameRules:[
        (v) => !!v || '이름을 작성해주세요',
      ],

      userNickname : "",
      nicknameRules:[
        (v) =>  !!v || '닉네임을 작성해주세요',
        (v) => (v && v.length <= 10) || '닉네임을 10글자를 넘을 수 없습니다.',
      ],
      userPhone:"",
      userPhoneRules:[
        (v) => !!v || '전화번호를 작성해주세요',
        (v) => (v && /^\d{11}$/.test(v)) || '숫자 11자리로 입력해주세요',
      ],
      userEmail: "",
      emailRules:[
        (v) => /.+@.+\..+/.test(v) || "이메일 형식으로 작성해주세요",
      ],


    }

  },
  computed:{
    passwordConfirmationRule() {
      return () =>
          this.userPassword !== this.userPasswordCheck || "패스워드가 일치하지 않습니다";
    }
  },
  methods: {
    signUpSubmit() {
      const formComponent = this.$refs.form;
      if (formComponent && formComponent.validate) {
        const validate = formComponent.validate();
        if (validate) {
          let saveData = {};
          saveData.userId = this.userId;
          saveData.userPassword = this.userPassword;
          saveData.userNickname = this.userNickname;
          saveData.userName = this.userName;
          saveData.userPhone=this.userPhone;
          saveData.userEmail = this.userEmail;

          try {
            this.$axios.post("/animate/user/register", JSON.stringify(saveData), {
              headers: {
                "Content-Type": `application/json`,
              },
            })
                .then((response) => {
                  console.log(response)
                  if (response.data.errorCode === 400) {
                    alert(response.data.errorMessage)

                  } else {
                    alert("회원가입이 완료되었습니다. 로그인 화면으로 돌아갑니다")
                    this.$router.push({path: './login'});
                  }
                })
                .catch(error => {
                  console.log(error.response);

                });
          } catch (error) {
            console.error(error);
          }
        }
      }
    },
  },

};
</script>

<style scoped>
.content{
  width: 768px;
  margin:auto;
}
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}

.b-example-divider {
  width: 100%;
  height: 3rem;
  background-color: rgba(0, 0, 0, .1);
  border: solid rgba(0, 0, 0, .15);
  border-width: 1px 0;
  box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}

.b-example-vr {
  flex-shrink: 0;
  width: 1.5rem;
  height: 100vh;
}

.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.nav-scroller {
  position: relative;
  z-index: 2;
  height: 2.75rem;
  overflow-y: hidden;
}

.nav-scroller .nav {
  display: flex;
  flex-wrap: nowrap;
  padding-bottom: 1rem;
  margin-top: -1px;
  overflow-x: auto;
  text-align: center;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
  --bd-violet-bg: #712cf9;
  --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

  --bs-btn-font-weight: 600;
  --bs-btn-color: var(--bs-white);
  --bs-btn-bg: var(--bd-violet-bg);
  --bs-btn-border-color: var(--bd-violet-bg);
  --bs-btn-hover-color: var(--bs-white);
  --bs-btn-hover-bg: #6528e0;
  --bs-btn-hover-border-color: #6528e0;
  --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
  --bs-btn-active-color: var(--bs-btn-hover-color);
  --bs-btn-active-bg: #5a23c8;
  --bs-btn-active-border-color: #5a23c8;
}

.bd-mode-toggle {
  z-index: 1500;
}

.bd-mode-toggle .dropdown-menu .active .bi {
  display: block !important;
}
.col-md-7.col-lg-8{
  margin: auto;
}
.fa {
  width:15px;
  height: 15px;
}
.row.g-3{
  margin-bottom: 50px
}
</style>