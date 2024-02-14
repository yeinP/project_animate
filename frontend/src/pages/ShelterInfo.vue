<template>
  <div class="container">
    <div class="shelters">
      <div class="shelters-list">
        <div v-for="shelter in shelters" :key="shelter.careNm" class="shelter-info">
          <div class="shelter-name">{{ shelter.careNm }}</div>
          <div class="shelter-details">
            <div class="address">{{ shelter.careAddr }}</div>
            <div class="phone">{{ shelter.careTel }}</div>
          </div>
        </div>
      </div>
      <div id="map" class="shelters-map"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      shelters: [],
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap()
    }else {
      const script = document.createElement('script');
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=a717c9681b58a48a6742a436e7fdd2f2';
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var mapContainer = document.getElementById('map'),
      mapOption = {
        center : new kakao.maps.LatLng(37.564343, 126.947613),
        level: 3,

      };
      var map = new kakao.maps.Map(mapContainer, mapOption);
      var positions = [
        {latlng: new kakao.maps.LatLng(37.562632898194835, 126.9454282268269),},
        {latlng: new kakao.maps.LatLng(37.56195884514403, 126.94922601468826),},

      ];
      positions.forEach(function (pos){
        var marker = new kakao.maps.Marker({
          position: pos.latlng,
        });
        marker.setMap(map);
      })
    }
  },

  created() {
    // API 호출해서 데이터 받아오기
    fetch('http://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo?numOfRows=20&pageNo=3&serviceKey=aQc0i7nxPnLiQlZAS7cmLwlDZjOT3fdCdEI7XY2VzJP57%2BS1B6Djo1EeqOtJX0t7C%2B%2F3OQ4G7K5Eklk%2FZooJmw%3D%3D')
        .then(response => response.text())
        .then(xmlData => {
          const parser = new DOMParser();
          const xmlDoc = parser.parseFromString(xmlData, 'application/xml');
          const items = xmlDoc.querySelectorAll('item');

          this.shelters = Array.from(items).map(item => {
            return {
              careNm: item.querySelector('careNm').textContent,
              orgNm: item.querySelector('orgNm').textContent,
              divisionNm: item.querySelector('divisionNm').textContent,
              saveTrgtAnimal: item.querySelector('saveTrgtAnimal').textContent,
              careAddr: item.querySelector('careAddr').textContent,
              jibunAddr: item.querySelector('jibunAddr').textContent,
              lat: parseFloat(item.querySelector('lat').textContent),
              lng: parseFloat(item.querySelector('lng').textContent),
              weekOprStime: item.querySelector('weekOprStime').textContent,
              weekOprEtime: item.querySelector('weekOprEtime').textContent,
              closeDay: item.querySelector('closeDay').textContent,
              careTel: item.querySelector('careTel').textContent
            };
          });
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
  },
};
</script>

<style>
#map {

  width: 800px;
  height: 800px;
}
.shelters {
  margin-top:10px;
  display: flex;
}


.shelter-info {
  margin-bottom: 20px;
  text-align: left;
}

.shelter-name {
  font-size: 18px;
  font-weight: bold;
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
  height: 800px;
  flex: 0.5;
  overflow-y: auto;
}

.shelters-map {
  flex: 1;
  width: 800px;
  height: 800px;
}
</style>