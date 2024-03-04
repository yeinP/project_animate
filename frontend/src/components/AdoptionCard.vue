<template>
    <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative card">
      <div class="col p-4 d-flex flex-column position-static marauto">
        <div class="animalInfo">
          <h5 class="d-inline-block mb-2 text-primary-emphasis">{{animal.kindCd}}</h5>
          <span>
            <i v-if="animal.sexCd === 'M'" class="fa fa-mars" aria-hidden="true"></i>
            <i v-else-if="animal.sexCd === 'F'" class="fa fa-venus" aria-hidden="true"></i>
            <span v-else>성별 미상</span>
          </span>
          <span>
            <span v-if="animal.neuterYn === 'Y'">(중성화 O)</span>
            <span v-else-if="animal.neuterYn === 'N'">(중성화 X)</span>
            <span v-else>(중성화 미상)</span>
          </span>
        </div>
        <table class="infotable">
          <tbody>
          <tr>
            <th class="nameTag">발견장소</th>
            <td>{{animal.happenPlace}}</td>
          </tr>
          <tr>
            <th class="nameTag">접수일</th>
            <td>{{animal.happenDt}}</td>
          </tr>
          <tr>
            <th class="nameTag">정보</th>
            <td>{{animal.colorCd}} / {{animal.age}} / {{animal.weight}}</td>
          </tr>
          <tr>
            <th class="nameTag" style="display: contents">특이사항</th>
            <td>{{animal.specialMark}}</td>
          </tr>
          <tr>
            <th class="nameTag">보호센터</th>
            <td>{{animal.careNm}} (Tel: {{animal.careTel}})</td>
          </tr>
          </tbody>
        </table>

      </div>
      <div class="col-auto d-none d-lg-block">
        <svg class="bd-placeholder-img" width="200" height="300" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
          <rect width="100%" height="100%" fill="#f8f9fa"></rect>
          <image :href="animal.popfile" alt="동물 이미지" width="100%" height="100%"></image>
        </svg>
      </div>
    </div>
</template>
<script>
export default {
  name:'AdoptionCard',
  props:['animal'],
  computed:{

  }

}

</script>
<style scoped>
.d-lg-block{
  border-left: 2px solid #abb0b4

}

.border{
  border: var(--bs-border-width) var(--bs-border-style) #abb0b4!important;
}
.animalInfo{
  display: flex;
  align-items: stretch;
}

.animalInfo strong{
  margin-right: 10px;
}

.animalInfo span{
  margin-right: 5px;
}

h5{
  font-weight: bolder;
  margin-right: 10px;
}
.infotable th{
  width: 20%;

}
.infotable tr{
  height: 30px;
}

.card {
  height: 300px;
}
.marauto{
  margin: auto;
}
</style>