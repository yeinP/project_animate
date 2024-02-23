<template>
  <div class="container">
    <div class="search-bar">
      <select v-model="searchOption">
        <option value="careNm">보호소명</option>
        <option value="careAddr">주소</option>
      </select>
      <input v-model="searchText" placeholder="검색어를 입력하세요"/>
      <button @click="filterShelters">검색</button>
    </div>
    <div class="shelters">
      <div class="shelters-list">
        <div v-if="shouldShowResults">
          <!-- Show the entire list when there is no search text -->
          <div v-for="shelter in (searchText ? filteredShelters : shelters)" :key="shelter.careNm" class="shelter-info">
            <div class="shelter-name" @click="moveToLocation(shelter.lat, shelter.lng, 3)">{{ shelter.careNm }}</div>
            <div class="shelter-details">
              <div class="address">{{ shelter.careAddr }}</div>
              <div class="phone">{{ shelter.careTel }}</div>
            </div>
          </div>
        </div>
        <div v-else>
          <p>검색 결과가 없습니다.</p>
        </div>
      </div>
<!--      <div class="shelters-list">-->
<!--        <div v-for="shelter in shelters" :key="shelter.careNm" class="shelter-info">-->
<!--          <div class="shelter-name" @click="moveToLocation(shelter.lat, shelter.lng,3)">{{ shelter.careNm }}</div>-->
<!--          <div class="shelter-details">-->
<!--            <div class="address">{{ shelter.careAddr }}</div>-->
<!--            <div class="phone">{{ shelter.careTel }}</div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
      <div id="map" class="shelters-map"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      shelters: [],
      map: null,
      markers: [],
      searchOption: 'careNm',
      searchText: "",
      filteredShelters: [],
      shouldShowResults: true,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=a717c9681b58a48a6742a436e7fdd2f2';
      document.head.appendChild(script);
    }
  },
  methods: {

    //카카오 지도 띄움
    async initMap() {
      var mapContainer = document.getElementById('map');
      var mapOption = {
        center: new kakao.maps.LatLng(36.5, 127.5),
        level: 12,
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption);
      await this.fetchSheltersData();

    },
    //api 정보가져옴
    async fetchSheltersData() {
      try {
        const response = await fetch('http://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo?numOfRows=194&pageNo=1&serviceKey=aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57%2BS1B6Djo1EeqOtJX0t7C%2B%2F3OQ4G7K5Eklk%2FZooJmw%3D%3D');
        const xmlData = await response.text();

        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlData, 'application/xml');
        const items = xmlDoc.querySelectorAll('item');

        this.shelters = Array.from(items).map(item => {
          return {
            careNm: item.querySelector('careNm')?.textContent || '',
            lat: parseFloat(item.querySelector('lat')?.textContent || '0'),
            lng: parseFloat(item.querySelector('lng')?.textContent || '0'),
            careAddr: item.querySelector('careAddr')?.textContent || '',
            careTel: item.querySelector('careTel')?.textContent || '',
          };
        });

        this.placeMarkers();
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    placeMarkers() {
      this.shelters.forEach(shelter => {
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(shelter.lat, shelter.lng),
        });
        marker.setMap(this.map);
        // this.markers.push(marker);
      });
    },
    //병원이름 클릭시 위치이동
    moveToLocation(lat, lng, level) {
      const moveLatLon = new kakao.maps.LatLng(lat, lng);
      this.map.setLevel(level,{animate:{duration: 500}})
      this.map.panTo(moveLatLon);
    },
    //검색
    filterShelters() {
      const searchText = this.searchText;
      const searchOption = this.searchOption;
      this.filteredShelters = this.shelters.filter(shelter => {
        const targetValue = shelter[searchOption];
        return targetValue.includes(searchText);
      });
      this.shouldShowResults = this.filteredShelters.length > 0 && searchText !== '';
    },
    showMarkerForFilteredShelters(){
      this.markers.forEach((marker => marker.setMap(null)));
      this.filteredShelters.forEach(shleter => {
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(shleter.lat, shleter.lng),
        });
        marker.setMap(this.map);
        this.markers.push(marker);
      });
    }
  },



  //         careNm: item.querySelector('careNm')?.textContent || '',
  //         orgNm: item.querySelector('orgNm')?.textContent || '',
  //         saveTrgtAnimal: item.querySelector('saveTrgtAnimal')?.textContent || '',
  //         careAddr: item.querySelector('careAddr')?.textContent || '',
  //         lat: parseFloat(item.querySelector('lat')?.textContent || '0'),
  //         lng: parseFloat(item.querySelector('lng')?.textContent || '0'),
  //         weekOprStime: item.querySelector('weekOprStime')?.textContent || '',
  //         weekOprEtime: item.querySelector('weekOprEtime')?.textContent || '',
  //         closeDay: item.querySelector('closeDay')?.textContent || '',
  //         careTel: item.querySelector('careTel')?.textContent || '',

};
</script>

<style>
#map {
  width: 800px;
  height: 800px;
}
.shelters {
  margin-top: 10px;
  display: flex;
}
.shelter-info {
  margin-bottom: 20px;
  text-align: left;
}
.shelter-name {
  font-size: 18px;
  font-weight: bold;
  cursor: pointer; /* 마우스 커서를 포인터로 변경하여 클릭 가능하다는 표시 */
}
.shelter-details {
  margin-top: 5px;
}
.address,
.phone {
  font-size: 14px;
  margin-bottom: 5px;
}
.shelters-list {
  height: 700px;
  flex: 0.5;
  overflow-y: auto;
}
.shelters-map {
  flex: 1;
  width: 800px;
  height: 700px;
}
</style>
