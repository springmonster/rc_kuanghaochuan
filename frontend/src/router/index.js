import { createRouter, createWebHistory } from 'vue-router'
import DestinationList from '../views/destination/DestinationList.vue'
import DestinationForm from '../views/destination/DestinationForm.vue'

const routes = [
  {
    path: '/',
    redirect: '/destinations'
  },
  {
    path: '/destinations',
    name: 'destination-list',
    component: DestinationList
  },
  {
    path: '/destinations/new',
    name: 'destination-new',
    component: DestinationForm
  },
  {
    path: '/destinations/:id/edit',
    name: 'destination-edit',
    component: DestinationForm
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
