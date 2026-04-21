<template>
  <div>
    <h1>作者頁</h1>

    <ul>
      <li v-if="author">
        <p>作者ID: {{ author.id }}</p>
        <p>作者名稱: {{ author.name }}</p>
      </li>
      <li v-else>
        <p>載入中...</p>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getAuthorById } from "../api/authorApi";
import type { AuthorResponse } from "../types/author";


const author = ref<AuthorResponse | null>(null);

onMounted(async () => {
  try {
    const res = await getAuthorById(1); // 這裡先測試 getAuthorById，看看資料長怎樣
    console.log(res); // ⭐ 一定先看資料長怎樣
    author.value = res;
  } catch (error) {
    console.error(error);
  }
});
</script>
