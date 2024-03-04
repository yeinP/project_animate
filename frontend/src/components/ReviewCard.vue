<template>
  <div class="card shadow-sm">
    <div class="card-body1">
      <div>작성자</div>
      <div>날짜</div>
    </div>
    <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
      <title>Placeholder</title>
      <rect width="100%" height="100%" fill="#55595c"/>

    </svg>
    <div class="card-body">
      <p class="card-text">Tㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅋㅇㅋㅇㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊhis is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
      <div class="d-flex justify-content-between align-items-center">
        <div class="btn-group">
          <button type="button" class="btn btn-sm btn-outline-secondary">좋아요</button>
          <button type="button" class="btn btn-sm btn-outline-secondary">댓글보기</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'ReviewCard',
}
</script>

<style scoped>

.card-body .d-flex{
  flex-direction: row-reverse;
}

.card-body1{
  flex: 1 1 auto;
  padding: var(--bs-card-spacer-y) var(--bs-card-spacer-x);
  color: var(--bs-card-color);
  display: flex;
  justify-content: space-between;
}
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}
</style>