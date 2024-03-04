<template>
<div class="container">
  <div class="album py-5">
    <div class="writeMissBtn">
      <div class="uploadBtn">
      <button @click="toggleModal" class="miss-btn"> <i class="fa fa-bullhorn" aria-hidden="true"></i>실종/목격 신고</button>
      </div>
      <div v-if="showModal" class="modal modal-sheet position-static d-block p-4 py-md-5" tabindex="-1" role="dialog" id="modalSheet">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
              <div class="title">
              <h1 class="modal-title fs-5">실종/목격 신고</h1>
              </div>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"  @click="closeModal"></button>
            </div>
            <div>
              <div class="line">
                <span>기본 정보</span>
              </div>
              <div class="selectedOption">
                <span class="req" style="margin-right: 0;">*</span><span>상태</span>
                <select v-model="mcStatus" class="selectedStatusOption">
                  <option value="1">실종</option>
                  <option value="2">제보</option>
                  <option value="3">완료</option>
                </select>
                <span style="color: orangered"> * 상황이 종료돈 후에는 완료로 수정해주세요.</span>
              </div>
              <div class="mcLoc">
                <span class="req" style="margin-right: 0;">*</span><span>지역</span>
                <select v-model="selectedLocOption1" ref="selectedLocOption1" @change="updateSecondOptions" class="selectedLocOption1">
                  <option value="allLoc">모든 지역</option>
                  <option value="gg">경기도</option>
                  <option value="daegu">대구광역시</option>
                  <option value="daejeon">대전광역시</option>
                  <option value="sejong">세종특별자치</option>
                  <option value="incheon">인천광역시</option>
                  <option value="jeonnam">전라남도</option>
                  <option value="kangwon">강원도</option>
                  <option value="ulsan">울산광역시</option>
                  <option value="jeonbuk">전라북도</option>
                  <option value="chungnam">충청남도</option>
                  <option value="jeju">제주특별자치도</option>
                  <option value="keongnam">경상남도</option>
                  <option value="keongbuk">경상북도</option>
                  <option value="chungbuk">충청북도</option>
                  <option value="gwangju">광주광역시</option>
                  <option value="busan">부산광역시</option>
                </select>
                <select v-model="selectedLocOption2" class="selectedLocOption2">
                  <option v-for="district in selectedDistricts" :key="district">{{ district }}</option>
                </select>
              </div>
              <div class="mcAddr">
                <span style="margin-left: 8px;">장소</span>
                <input type="text" v-model="mcAddr" placeholder="구체적인 장소를 적어주세요">
              </div>
              <div class="line">
                <span>동물 정보</span>
              </div>
              <div class="breed">
                <span>품종</span>
                <input type="radio" v-model="mcBreed" value="모든 동물" name="Breed"> <span>모든 동물</span>
                <input type="radio" v-model="mcBreed" value="고양이" name="Breed"> <span>고양이</span>
                <input type="radio" v-model="mcBreed" value="강아지" name="Breed"> <span>강아지</span>
              </div>

              <div class="gender">
                <span>성별</span>
                <input type="radio" v-model="mcGender" value="미확인" name="gender"> <span>미확인</span>
                <input type="radio" v-model="mcGender" value="수컷" name="gender"> <span>수컷</span>
                <input type="radio" v-model="mcGender" value="암컷" name="gender"> <span>암컷</span>
              </div>
              <div class="ageWeight">
                <div class="age">
                  <span>나이</span>
                  <select v-model="mcAge">
                    <option value="99">미확인</option>
                    <option value="0">1살 이하</option>
                    <option value="1">1살</option>
                    <option value="2">2살</option>
                    <option value="3">3살</option>
                    <option value="4">4살</option>
                    <option value="5">5살</option>
                    <option value="6">6살</option>
                    <option value="7">7살</option>
                    <option value="8">8살</option>
                    <option value="9">9살</option>
                    <option value="10">10살</option>
                    <option value="11">10살 이상</option>
                  </select>
                </div>

                <div class="weight">
                  <span>몸무게</span>
                  <select v-model="mcWeight">
                    <option value="99">미확인</option> <option value="0">1kg 미만</option> <option value="1">1kg</option>
                    <option value="2">2kg</option> <option value="3">3kg</option> <option value="4">4kg</option>
                    <option value="5">5kg</option> <option value="6">6kg</option> <option value="7">7kg</option>
                    <option value="8">8kg</option> <option value="9">9kg</option> <option value="10">10kg</option>
                    <option value="11">11kg</option> <option value="12">12kg</option>  <option value="13">13kg</option>
                    <option value="14">14kg</option> <option value="15">15kg</option>  <option value="16">16kg</option>
                    <option value="17">17kg</option> <option value="18">18kg</option>  <option value="19">19kg</option>
                    <option value="20">20kg</option> <option value="21">21kg</option>  <option value="22">22kg</option>
                    <option value="23">23kg</option> <option value="24">24kg</option>  <option value="25">25kg</option>
                    <option value="26">20kg</option> <option value="27">27kg</option>  <option value="28">28kg</option>
                    <option value="29">20kg</option> <option value="30">30kg</option>  <option value="31">30kg 이상</option>
                  </select>
                </div>
              </div>
              <div class="color">
                <span>털색</span>
                <input type="text" placeholder="털색을 설명해주세요" v-model="mcColor">
              </div>
              <div class="char">
                <span>특징</span>
                <input type="text" placeholder="특징을 작성해주세요" v-model="mcChar">
              </div>
              <div class="etc">
                <span>기타</span>
                <input type="text" placeholder="기타사항을 입력해주세요" v-model="mcEtc">
              </div>
            </div>
            <div class="modal-body py-0" >
              <div>
                <label for="fileInput" class="custom-file-upload">
                  <i class="fa fa-cloud-upload"></i> <span>파일 선택</span>
                </label>
                <span style="margin-left: 0.5rem; font-size: 15px">(사진은 1개 이상 최대 3개까지 선택해주세요.)</span>
                <input id="fileInput" ref="fileInput" type="file" @change="handleFileChange" style="display: none;"  multiple/>
              </div>
              <div  class="selected-images-container">
                <div v-for="(file, index) in selectedFiles" :key="index" class="selected-image">
                  <img :src="file.preview" alt="Selected Image" style="max-width: 100px; max-height: 100px; margin-right: 10px;" />
                  <button @click="removeImage(index)" class="remove-image-button">X</button>
                </div>
              </div>
            </div>
            <div class="modal-footer flex-column align-items-stretch w-100 gap-2 pb-3 border-top-0">
              <div class="d-flex justify-content-end">
              <button type="button" class="btn btn-lg btn-primary" @click="upload">작성</button>
              <button type="button" class="btn btn-lg btn-secondary" @click="closeModal">취소</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
      <div class="col" v-for="missCare in paginatedMissCareList" :key="missCare.mcNo">
        <MissCard :missCare="missCare"/>
      </div>
    </div>
    <div class="pagination d-flex justify-content-center" >
      <button @click="prevPage" :disabled="currentPage === 1"><i class="fa fa-chevron-left" aria-hidden="true"></i></button>
      <span v-for="page in totalPages" :key="page" @click="goToPage(page)" :class="{ active: page === currentPage }" style="margin: 0 5px 0 5px; cursor: pointer">{{ page }}</span>
      <button @click="nextPage" :disabled="currentPage * itemsPerPage >= missCareList.length"><i class="fa fa-chevron-right" aria-hidden="true"></i></button>
    </div>
  </div>

</div>

</template>
<script>


import MissCard from "@/components/MissCard.vue";
import axios from "axios";
import {onMounted, ref} from "vue";
import store from "@/scrpits/store";
import router from "@/scrpits/router";



export default {
  name:"Miss",
  components: {MissCard},
  setup(){
    const userNo = ref(store.state.user.userNo);
    onMounted(()=>{
      store.watch(()=> store.state.user.userNo,(newValue) =>{
        userNo.value = newValue;
      });
    });
    return { userNo };
  },
  data() {
    return {
      missCareList:[],
      currentPage: 1,
      itemsPerPage: 9,
      totalPages:0,
      showModal: false,
      selectedFiles: [],
      selectedOption: '1',
      selectedLocOption1: 'allLoc',
      selectedLocOption2: '전체',
      mcGender:"미확인",
      mcBreed:"모든 동물",
      mcAge:"99",
      mcWeight:"99",
      mcAddr:"",
      mcStatus:"0",
      mcColor:"",
      mcChar:"",
      mcEtc:"",
      allLoc:['전체'],
      gg : ['고양시', '과천시', '광명시', '광주시', '구리시', '군포시', '김포시', '남양주시', '동두천시',
          '부천시', '성남시', '수원시', '시흥시', '안산시', '안성시', '안양시',
          '양주시', '여주시', '오산시', '용인시', '의왕시', '의정부시', '이천시',
          '파주시', '평택시', '포천시', '하남시', '화성시'],
      seoul:[
          '강남구', '강동구', '강북구', '강서구', '관악구', '광진구',
          '구로구', '금천구', '노원구', '도봉구', '동대문구', '동작구',
          '마포구', '서대문구', '서초구', '성동구', '성북구', '송파구',
          '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구'
        ],
      daegu:[
        '남구', '달서구', '달성군', '동구', '북구', '서구'
      ],
      daejeon: [
        '대덕구', '동구', '서구', '유성구', '중구'
      ],
      sejong:['전체'],
      incheon:[
          '강화군', '계양구', '남동구', '동구', '미추홀구', '부평구', '서구', '연수구', '옹진군'
        ],
      jeonnam: [
        '강진군', '고흥군', '곡성군', '광양시', '구례군', '나주시', '담양군', '목포시', '무안군',
        '보성군', '순천시', '신안군', '여수시', '영광군', '영암군', '완도군', '장성군', '장흥군',
        '진도군', '함평군', '해남군', '화순군'
      ],
      kangwon : [
        '강릉시', '고성군', '동해시', '삼척시', '속초시', '양구군', '양양군', '영월군', '원주시',
        '인제군', '정선군', '철원군', '춘천시', '태백시', '평창군', '홍천군', '화천군', '횡성군'
      ],
      ulsan : [
        '남구', '동구', '북구', '울주군', '중구'
      ],
      jeonbuk : [
        '고창군', '군산시', '김제시', '남원시', '무주군', '부안군', '순창군', '완주군', '익산시',
        '임실군', '장수군', '전주시', '정읍시', '진안군'
      ],
      chungnam : [
        '계룡시', '공주시', '금산군', '논산시', '당진시', '보령시', '부여군', '서산시', '서천군',
        '아산시', '예산군', '천안시', '청양군', '태안군', '홍성군'
      ],
      jeju : [
        '서귀포시', '제주시'
      ],
      keongnam : [
        '거제시', '거창군', '고성군', '김해시', '남해군', '밀양시', '사천시', '산청군', '양산시',
        '의령군', '진주시', '창녕군', '창원시', '통영시', '하동군', '함안군', '함양군', '합천군'
      ],
      keongbuk : [
        '경산시', '경주시', '고령군', '구미시', '군위군', '김천시', '문경시', '봉화군', '상주시',
        '성주군', '안동시', '영덕군', '영양군', '영주시', '영천시', '예천군', '울릉군', '울진군',
        '의성군', '청도군', '청송군', '칠곡군', '포항시'
      ],
      chungbuk : [
        '괴산군', '단양군', '보은군', '영동군', '옥천군', '음성군', '제천시', '증평군', '진천군',
        '청원군', '청주시', '충주시'
      ],
      gwangju : [
        '광산구', '남구', '동구', '북구', '서구'
      ],
      busan : [
        '강서구', '금정구', '기장군', '남구', '동구', '동래구', '부산진구', '북구', '사상구',
        '사하구', '서구', '수영구', '연제구', '영도구', '중구', '해운대구'
      ],
   };
  },
  mounted() {
    this.getMissCareDtoList();
  },
  computed:{
    selectedDistricts() {
      return this[this.selectedLocOption1] || [];
    },
    paginatedMissCareList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.missCareList.slice(start, end);
    },
  },
  methods:{
    nextPage() {
      if (this.currentPage * this.itemsPerPage < this.missCareList.length) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    goToPage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    getMissCareDtoList(){
      axios.get('http://localhost:9090/animal/miss_care')
          .then((response) => {
            this.missCareList = response.data;
            this.totalPages = Math.ceil(this.missCareList.length / this.itemsPerPage);

            if (this.currentPage > this.totalPages) {
              this.currentPage = this.totalPages;
            }
          })
          .catch((error) => {
            console.error('데이터를 가져오는 중 오류 발생:', error);
          });
    },
    updateSecondOptions() {
      this.selectedLocOption2 = "";
    },
    upload(){
      const uploadconfirm = confirm("작성하시겠습니까?")
      if(uploadconfirm == true){
        const formData = new FormData();
        const files = this.$refs.fileInput.files;

        if (files.length > 0) {
          for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
          }
        } else {
          alert("사진을 1장 이상 추가해주세요.");
          return ;
        }
        const selectedLocText1 = this.$refs.selectedLocOption1.options[this.$refs.selectedLocOption1.selectedIndex].text;
        const mcLoc = `${selectedLocText1} ${this.selectedLocOption2}`;
        formData.append('mcLoc', mcLoc);
        formData.append('mcStatus', this.mcStatus);
        formData.append('mcBreed', this.mcBreed);
        formData.append('mcGender', this.mcGender);
        formData.append('mcAge', this.mcAge);
        formData.append('mcWeight', this.mcWeight);
        formData.append('mcColor', this.mcColor);
        formData.append('mcChar', this.mcChar);
        formData.append('mcEtc', this.mcEtc);
        formData.append('mcAddr', this.mcAddr);


        axios.post('/animate/upload/mcAnimal', formData)
            .then(response => {
              window.alert("작성완료되었습니다.")
              this.$router.push({ path: '/animal/miss_care'}).then(() => {
                window.location.reload();
              })
              console.log(response);
            })
            .catch(error => {
              // Handle the error if needed
              console.error(error);
            });

      } else{
        return false;
      }

    },
    toggleModal() {
      if (!this.userNo) {
        alert("로그인 후 작성 가능합니다.")
        router.push({path: '/login'})
      } else{
      this.showModal = !this.showModal;
      }
    },
    closeModal() {
      const check = confirm("작성을 취소하시겠습니까?");
      if(check){
        this.showModal = false;
      }else {
        this.showModal = true;
      }

    },
    handleFileChange(event) {
      const filesArray = Array.from(event.target.files);
      if (filesArray.length > 3) {
        alert('사진 선택은 최대 3장까지 가능합니다.');
        return;
      }
      this.selectedFiles = filesArray;
      for (let i = 0; i < this.selectedFiles.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.selectedFiles[i] = {
            file: this.selectedFiles[i],
            preview: e.target.result,
          };
        };
        reader.readAsDataURL(this.selectedFiles[i]);
      }
    },
    removeImage(index) {
      const updatedSelectedFiles = [...this.selectedFiles];

      updatedSelectedFiles.splice(index, 1);

      this.selectedFiles = updatedSelectedFiles;
    },

  },


}

</script>
<style scoped>
.selected-images-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
}

.selected-image {
  margin-right: 10px;
}

.modal-content{
  height: 100%;
}

.modal-dialog{
  max-width:600px;
}
.modal-body{
  display: grid;
}
.remove-image-button {
  position: initial;
  top: 5px;
  right: 5px;
  background: none;
  border: none;
  color: black;
  font-size: 20px;
  cursor: pointer;
}
.modal-footer{
  display: flow;
}
.btn-lg{
  margin:3px;
}

.selectedOption, .mcAddr, .mcLoc{
  padding: 0.5rem 1rem;
}

.mcLoc option{
  width: 125px;
  text-align: center;
}
.selectedOption span, .selectedOption select, .mcAddr span, .mcLoc span{
  margin-right: 1rem;
}
.mcAddr input{
  width: 250px;
}
.line{
  border-radius: inherit;
  text-align: center;
  color: #6b6b6b;
  background-color: #dedd657a;
  height: 30px;
}

.line span{
  margin: auto;
  font-size: 18px;
  font-weight: bolder;
}
.selectedLocOption2 {
  width: 125px;
}
.selectedLocOption1 {
  margin-right: 10px;
}
.selectedStatusOption{
  text-align: center;
  width: 125px;
}
.gender, .age, .weight, .breed, .color, .etc, .char{
  padding: 0.5rem 1.4rem;
}
.gender span, .age span, .weight span, .breed span, .color span, .etc span, .char span{
  margin-right:1rem;
}
.ageWeight{
  display: flex;
  align-items: center;
}

.color input, .etc input {
  width: 400px;
}
.custom-file-upload {
  margin-top:2px;

  display: inline-block;
  cursor: pointer;
}
.req{
  color: red;
  margin-right: 0;
}

.custom-file-upload{
  margin-left: 0.5rem;

}


.miss-btn i{
  margin-right: 0.5rem;
}
.uploadBtn{
  display: flex;
  flex-direction: row-reverse;
  margin: 0 0 0.5rem;
}

.miss-btn{
  width: 180px;
  height: 35px;
  background-color: rgba(205, 205, 103, 1);
  border: 0;
  color: #fff;
  border-radius: 4px;
  font-weight: bold;
}

.pagination{
  margin-top: 1rem;
}

.pagination button{
 background-color: white;
  border: white;
}

</style>
