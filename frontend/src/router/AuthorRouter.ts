
import AuthorDetail from '../views/AuthorDetail.vue'
import AuthorList from '../views/AuthorDetail.vue'


export default [
    {
      path: '/authors',
      name: 'authors',
      component: AuthorList,
    },
    {
      path: '/authors/:id',//動態參數
      name: 'authorDetail',
      component: AuthorDetail, // 你現在那個頁面
    }
  ]

