<template>
<div class="container">
  <div class="album py-5">
    <div class="writeMissBtn">
    <button @click="toggleModal" class="miss-btn">실종/보호 신고</button>
      <div v-if="showModal" class="modal modal-sheet position-static d-block p-4 py-md-5" tabindex="-1" role="dialog" id="modalSheet">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
              <div class="title">
              <h1 class="modal-title fs-5">실종/보호 신고</h1>
              </div>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"  @click="closeModal"></button>
            </div>
            <div class="line">
              <span>기본 정보</span>
            </div>
            <div class="selectedOption">
              <span>상태</span>
              <select v-model="selectedOption" class="selectedStatusOption">
                <option value="1">실종</option>
                <option value="2">제보</option>
                <option value="3">완료</option>
              </select>
              <span style="color: orangered"> * 상황이 종료돈 후에는 완료로 수정해주세요.</span>
            </div>
            <div class="mcLoc">
              <span>지역</span>
              <select v-model="selectedLocOption1" @change="updateSecondOptions" class="selectedLocOption1">
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
              <span>장소</span>
              <input type="text" v-model="mcContent" placeholder="구체적인 장소를 적어주세요">
            </div>
            <div class="modal-body py-0" style="grid-template-rows: 0.5fr 0.5fr 2fr;">
              <input type="file" ref="fileInput" @change="handleFileChange" multiple />
              <div  class="selected-images-container">
                <div v-for="(file, index) in selectedFiles" :key="index" class="selected-image">
                  <img :src="file.preview" alt="Selected Image" style="max-width: 100px; max-height: 100px; margin-right: 10px;" />
                  <button @click="removeImage(index)" class="remove-image-button">X</button>
                </div>
              </div>
              <textarea v-model="mcContent" ></textarea>
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
    <div class="container">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" v-for="i in 12" :key="i">
        <MissCard/>
        </div>
      </div>
    </div>
  </div>

</div>

</template>
<script>


import MissCard from "@/components/MissCard.vue";
import axios from "axios";

export default {
  name:"Miss",
  components: {MissCard},
  data() {
    return {
      showModal: false,
      missContent: "",
      selectedFiles: null,
      selectedOption: '1',
      selectedLocOption1: 'allLoc',
      selectedLocOption2: '전체',
      mcAddr:"",
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
  computed:{
    selectedDistricts() {
      return this[this.selectedLocOption1] || [];
    },
  },
  methods:{
    updateSecondOptions() {
      this.selectedLocOption2 = "";
    },
    upload(){
      const formData = new FormData();
      const files = this.$refs.fileInput.files;

      for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
      }
      const selectedLocText1 = this.$refs.selectedLocOption1.options[this.$refs.selectedLocOption1.selectedIndex].text;
      const mcAddr = `${selectedLocText1} ${this.selectedLocOption2}`;
      formData.append('mcAddr', mcAddr);
      formData.append('mcStatus', this.selectedOptions);
      console.log(mcAddr);

      axios.post('/animate/upload/mcAnimal', formData)
          .then(response => {
            // Handle the response if needed
            console.log(response);
          })
          .catch(error => {
            // Handle the error if needed
            console.error(error);
          });
    },
    toggleModal() {
      this.showModal = !this.showModal;
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
  height: 600px;
}

.modal-dialog{
  max-width:800px;
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

.selectedOption{
  padding: 0.5rem 1rem;
}
.selectedOption span, .selectedOption select{
  margin-right: 0.5rem;
}
.mcLoc option{
  width: 125px;
  text-align: center;
}
.mcAddr, .mcLoc{
  padding: 0.5rem 1rem;
}

.mcAddr span, .mcLoc span{
  margin-right: 0.5rem;
}
.mcAddr input{
  width: 250px;
}
.line{
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
</style>
