<template>

  <div class="modal" v-if="showModal">
    <MissCareModal :missCare = "missCare" @close="closeModal" :mcNo = "missCare.mcNo" :modalData = "modalData" :replyData = "replyData" :reReplyData = "reReplyData"></MissCareModal>
  </div>

  <div class="card shadow-sm" @click="openModal">
    <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
      <title>Placeholder</title>
      <rect width="100%" height="100%" fill="#eae8e8bf"></rect>
      <image :href="missCare.mcImgUrl" x="10%"  width="80%" height="100%" preserveAspectRatio="xMidYMid slice"></image>
    </svg>
    <div class="card-body">
      <div class="card-body-title">
        <div class="msStatusBox" disabled :class="msStatusClass">{{ mcStatusLabel }}</div>
        <p class="card-text">{{missCare.mcBreed}}</p>
      </div>
      <div class="align-items-center d-flex justify-content-end">

        <small class="text-body-secondary">{{missCare.mcLoc}}</small>
        <i class="fa fa-map-marker"></i>
      </div>
      <div class="align-items-center d-flex justify-content-end">

        <small class="text-body-secondary">{{formattedDate}}</small>
        <i class="fa fa-calendar"></i>
      </div>
    </div>
  </div>

</template>

<script>
import moment from "moment";
import MissCareModal from "@/components/MissCareModal.vue";
import axios from "axios";

export default {
name:"MissCard",
  props:['missCare'],
  components:{MissCareModal},
  data(){
    return {
      showModal: false,
      modalData:[],
      replyData:[],
      reReplyData:[],
    }
  },
  computed:{
    mcStatusLabel(){
      switch (this.missCare.mcStatus){
        case 1:
          return "실종";
        case 2:
          return "제보";
        default:
          return "완료";
      }
    },
    msStatusClass() {
      switch (this.missCare.mcStatus) {
        case 1:
          return "btn-miss";
        case 2:
          return "btn-care";
        default:
          return "btn-comp";
      }
    },
    formattedDate(){
      return moment(this.missCare.mcRegdate).format('YYYY.MM.DD');
    }
  },
  methods:{
    openModal() {
      this.showModal = true;
      const apiUrl = `http://localhost:9090/animal/miss_care/modal/${this.missCare.mcNo}`;

      // 모달 데이터 가져오기
      axios.get(apiUrl)
          .then(response => {
            this.modalData = response.data;
          })
          .catch(error => {
            console.error("모달 데이터를 가져오는 중 오류 발생:", error);
            // 에러 처리 로직 추가
          });

      // 댓글 데이터 가져오기
      axios.get(`${apiUrl}/reply`)
          .then(response => {
            this.replyData = response.data;
          })
          .catch(error => {
            console.error("댓글 가져오는 중 오류:", error);
          });

      axios.get(`${apiUrl}/reply/re`)
          .then(response =>{
            this.reReplyData = response.data;
          }).catch(error => {
            console.error("대댓글 가져오는 중 오류:", error);
      })
  },

    closeModal() {
      this.showModal = false;
    },

  }

}
</script>



<style scoped>
.align-items-center{
  margin:0 5px;
}
.align-items-center i{
  margin-left: 10px;
}
image{
  margin: auto;
}

.btn-miss{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #ff5656;
  background-color: #ff5656;
  color: white;
  font-weight: 600;
  margin-right: 10px;
}

.btn-care{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #e7c300;
  background-color: #e7c300;
  color: #ffffff;
  font-weight: 600;
  margin-right: 10px;
}
.btn-comp{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #696969;
  background-color: #696969;
  color: #ffffff;
  font-weight: 600;
  margin-right: 10px;
}

.card-body-title{
display: flex;
}
.fa-map-marker{
  padding-right: 3px;
}
.card {
  cursor: pointer;
}
.modal{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>