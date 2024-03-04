<template>
<div class="container">
  <div class="alter">
    <i class="fa fa-exclamation" aria-hidden="true"></i><sapn> 입양 문의는 보호센터로 연락 바랍니다.</sapn>
  </div>
  <div class="searchBar d-flex justify-content-center">
    <div class="date">
      <span>날짜</span>
      <input type="date" v-model="startDate" class="form-control" id="startDate" :max="endDate">~<input type="date" v-model="endDate" class="form-control" id="endDate">
    </div>
    <div class="breed">
      <span>품종</span>
      <select v-model="upkind" class="search-barBtn">
        <option value="">전체</option>
        <option value="417000">개</option>
        <option value="422400">고양이</option>
        <option value="429900">기타</option>
      </select>
    </div>
    <div class="loc">
      <span>지역</span>
      <select v-model="selectedSido" @change="fetchSigunguList" class="search-barBtn">
        <option value="">모든지역</option>
        <option v-for="sido in sidoList" :key="sido.orgCd" :value="sido.orgCd">
          {{ sido.orgdownNm }}
        </option>
      </select>
      <select v-model="selectedSigungu" class="search-barBtn">
        <option value="">전체</option>
        <option v-for="sigungu in sigunguList" :key="sigungu.orgCd" :value="sigungu.orgCd">
          {{ sigungu.orgdownNm }}
        </option>
      </select>
    </div>
    <div class="searchBtn">
      <button @click="search">검색</button>
    </div>
  </div>
  <div class="row mb-2">
    <div class="col-md-6" v-for="animal in adoptionAnimals" :key="animal.desrtionNo">
      <AdoptionCard :animal="animal" />
    </div>
  </div>
  <div class="pagination d-flex justify-content-center">
    <button @click="prevPageGroup" :disabled="currentPageGroup === 1">
      <i class="fa fa-chevron-left" aria-hidden="true"></i>
    </button>
    <span v-for="page in displayedPages" :key="page">
      <button @click="goToPage(page)" :class="{ 'active': currentPage === page }">{{ page }}</button>
    </span>
    <button @click="nextPageGroup" :disabled="currentPageGroup === maxPageGroup">
      <i class="fa fa-chevron-right" aria-hidden="true"></i>
    </button>
  </div>
</div>


</template>

<script>

import axios from "axios";
import AdoptionCard from "@/components/AdoptionCard.vue";

export default {
  name: "Adoption",
  components: {AdoptionCard},

  data(){
    const today = new Date();
    const oneMonthAgo = new Date(today);
    oneMonthAgo.setMonth(today.getMonth() - 1);

    return {
      adoptionAnimals: [],
      sidoList: [],
      sigunguList: [],
      startDate: oneMonthAgo.toISOString().split("T")[0],
      endDate: today.toISOString().split("T")[0],
      upkind: '',
      selectedSido: '',
      selectedSigungu: '',
      currentPage: 1,
      totalPages: 1,
      pagesPerGroup: 10,
      currentPageGroup: 1,


    }
  },
  computed: {
    maxPageGroup() {
      return Math.ceil(this.totalPages / this.pagesPerGroup);
    },
    displayedPages() {
      const startPage = (this.currentPageGroup - 1) * this.pagesPerGroup + 1;
      const endPage = Math.min(startPage + this.pagesPerGroup - 1, this.totalPages);
      return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    },
  },
  mounted() {
    this.currentPage = 1;
    this.fetchAdoptionAnimalData();
    this.fetchSidoList();
    this.fetchSigunguList();
  },
  methods: {
    goToPage(page) {
      console.log('goToPage', page);
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
        this.fetchAdoptionAnimalData();
      }
    },

    async search() {
      console.log("검색");
      try {
        const response = await axios.get('http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic', {
          params: {
            bgnde: this.formatDate(new Date(this.startDate)),
            endde: this.formatDate(new Date(this.endDate)),
            upkind: this.upkind,
            upr_cd: this.selectedSido,
            org_cd: this.selectedSigungu,
            pageNo: this.currentPage,
            numOfRows: '10',
            serviceKey: 'aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57+S1B6Djo1EeqOtJX0t7C+/3OQ4G7K5Eklk/ZooJmw==',
            _type: 'json',
          },
        });

        console.log(response.data); // 여기에 응답 데이터를 콘솔에 출력

        if (response.data && response.data.response && response.data.response.body && response.data.response.body.items) {
          this.adoptionAnimals = Array.isArray(response.data.response.body.items.item)
              ? response.data.response.body.items.item
              : [response.data.response.body.items.item];
        } else {
          console.error('응답 구조가 예상과 다릅니다:', response);
        }
      } catch (error) {
        console.error('검색 중 에러 발생:', error);
      }
    },

    formatDate(date) {
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}${month}${day}`;
    },
    async fetchAdoptionAnimalData() {
      try {
        const response = await axios.get('http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic', {
          params: {
            bgnde: this.formatDate(new Date(this.startDate)),
            endde: this.formatDate(new Date(this.endDate)),
            pageNo: this.currentPage,
            numOfRows: '10',
            serviceKey: 'aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57+S1B6Djo1EeqOtJX0t7C+/3OQ4G7K5Eklk/ZooJmw==',
            _type: 'json',
          },
        });

        if (response.data && response.data.response && response.data.response.body && response.data.response.body.items) {
          this.adoptionAnimals = Array.isArray(response.data.response.body.items.item)
              ? response.data.response.body.items.item
              : [response.data.response.body.items.item];
          this.totalPages = response.data.response.body.totalCount
              ? Math.ceil(response.data.response.body.totalCount / 10)
              : 1;
        } else {
          console.error('응답 구조가 예상과 다릅니다:', response);
        }
      } catch (error) {
        console.error('입양 동물 데이터를 가져오는 중 에러 발생:', error);
      }
    },
    async fetchSidoList() {
      try {
        const response = await fetch("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido?numOfRows=17&pageNo=1&serviceKey=aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57%2BS1B6Djo1EeqOtJX0t7C%2B%2F3OQ4G7K5Eklk%2FZooJmw%3D%3D");
        const xmlData = await response.text();
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlData, 'application/xml');
        const items = xmlDoc.querySelectorAll('item');

        this.sidoList = Array.from(items).map(item => {
          return {
            orgCd: item.querySelector('orgCd')?.textContent || '',
            orgdownNm: item.querySelector('orgdownNm')?.textContent || '',
          };
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    async fetchSigunguList(){
      try{
        const selectedSido = this.sidoList.find(sido => sido.orgCd === this.selectedSido);
        if(selectedSido){
          const response = await fetch(`http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu?upr_cd=${selectedSido.orgCd}&serviceKey=aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57%2BS1B6Djo1EeqOtJX0t7C%2B%2F3OQ4G7K5Eklk%2FZooJmw%3D%3D`);
          const xmlData = await response.text();
          const parser = new DOMParser();
          const xmlDoc = parser.parseFromString(xmlData, 'application/xml');
          const items = xmlDoc.querySelectorAll('item');

          this.sigunguList = Array.from(items).map(item => {
            return {
              uprCd: item.querySelector('uprCd')?.textContent || '',
              orgCd: item.querySelector('orgCd')?.textContent || '',
              orgdownNm: item.querySelector('orgdownNm')?.textContent || '',
            };
          });
        } else {
          console.error('Selected sido not found');
        }
      }catch(error){
        console.error('Error fetching data:', error);
      }
    },
    prevPageGroup() {
      if (this.currentPageGroup > 1) {
        this.currentPageGroup--;
        this.fetchAdoptionAnimalData();
      }
    },
    nextPageGroup() {
      if (this.currentPageGroup < this.maxPageGroup) {
        this.currentPageGroup++;
        this.fetchAdoptionAnimalData();
      }
    },

  },



};
</script>

<style scoped>
.searchBar{
  display: flex;
  margin: 1rem 0 1rem 0;

}
.date, .breed .loc{
  display: flex;
  align-items: baseline;
}



.searchBar button{
  width: 56px;
  height: 35px;
  background-color: rgba(205, 205, 103, 1);
  border: 0;
  color: #fff;
  border-radius: 4px;
}
.date span , .breed span, .loc span{
  margin-right: 0.5rem;
  width: 90px;
  font-size: 16px;
  font-weight: bolder;
}
.date, .breed {
  margin-right: 1rem ;
}
.pagination{
  margin-top: 1rem;
}

.pagination button{
  background-color: white;
  border: white;
}
.alter{
  text-align: center;
  color: orangered;
}

</style>




