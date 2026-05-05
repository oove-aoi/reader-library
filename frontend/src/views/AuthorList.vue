<template>
  <div>
    <h1>作者列表頁</h1>
    <!-- 載入中 -->
      <div v-if="loading">
        載入中...
      </div>

      <!-- 找不到 -->
      <div v-else-if="error === 'NOT_FOUND'">
        查無相關聯作者
      </div>

      <!-- 其他錯誤 -->
      <div v-else-if="error">
        發生錯誤
      </div>

      <!-- 成功載入 -->
      <div v-else-if="authorList.length > 0">
        <ul>
          <li v-for="author in authorList" :key="author.id">
            <router-link :to="`/authors/${author.id}`">{{ author.name }}</router-link>
          </li>
        </ul>
  </div>
</template>


<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import type { AuthorResponse } from "../types/author";
import axios from "axios";

const route = useRoute(); // 取得路由資訊
//新增loading、error處理讀取中與錯誤狀態以利於後續處理
const author = ref<AuthorResponse | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);

onMounted(async () => {
  try {


  } catch (err) { //刻意不寫型別維持unknown
    //判斷是否為404
    if (axios.isAxiosError(err)) {
      const status = err.response?.status;

      switch(status) {
        case 404:
          error.value = "NOT_FOUND";
          break;
        case 500:
          error.value = "SERVER_ERROR";
          break;
        default:
          error.value = "ERROR";
      }
    } else {
      error.value = "UNKNOWN_ERROR";
    }
  } finally {
    loading.value = false; //一定要關掉 loading
  }
});
</script>
