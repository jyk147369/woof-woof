<template>
  <div>
    <h1>검색 결과</h1>
    <p>검색어: {{ route.query.q }}</p>
    <!-- 검색 결과를 여기에 표시합니다. -->
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

onMounted(() => {
  const searchTerm = route.query.q;
  // 검색어를 사용하여 검색 API 호출 또는 결과 필터링
  console.log('검색어:', searchTerm);
});
</script>
