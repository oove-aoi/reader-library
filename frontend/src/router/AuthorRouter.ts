
import AuthorDetail from '../views/AuthorDetail.vue'
import AuthorList from '../views/AuthorList.vue'
import AuthorNewAndEdit from '../views/AuthorCreateAndEdit.vue'


export default [
    {
      path: '/authors',
      name: 'authors',
      component: AuthorList,
    },
    {
      path: '/authors/:id',//動態參數
      name: 'authorDetail',
      component: AuthorDetail,
    },
    {
      path: '/authors/new',
      name: 'authorCreate',
      component: AuthorNewAndEdit,
    },
    {
      path: '/authors/:id/edit',//動態參數
      name: 'authorEdit',
      component: AuthorNewAndEdit,
    }
  ]

