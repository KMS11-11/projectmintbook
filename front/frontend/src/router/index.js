import { createRouter, createWebHistory } from 'vue-router'

//유저 파트
import MainPage from '../views/MainPage'
import SearchPage from '../views/SearchPage'
import BookDetailPage from '../views/BookDetailPage'
import CartPage from '../views/CartPage'
import OrderPage from '../views/OrderPage'
import OrderResultPage from '../views/OrderResultPage'
import BestsellerPage from '../views/BestsellerPage'
import NewBookPage from '../views/NewBookPage'
import CategoryPage from '../views/CategoryPage'
import EditorPickPage from '../views/EditorPickPage'
import EventPage from '../views/EventPage'
import EventDetailPage from '../views/EventDetailPage'
import CSPage from '../views/CSPage'
import CSRegisterPage from '../views/CSRegisterPage'
import FAQPage from '../views/FaqPage'
import NoticePage from '../views/NoticePage'
import NoticeDetailPage from '../views/NoticeDetailPage'

//관리자 파트
import ALoginPage from '../views/Admin/ALoginPage'
import ABookPage from '../views/Admin/ABookPage'
import ABookSearchPage from '../views/Admin/ABookSearchPage'
import ABookDetailPage from '../views/Admin/ABookDetailPage'
import ABookRegisterPage from '../views/Admin/ABookRegisterPage'
import ABookEditPage from '../views/Admin/ABookEditPage'
import AOrderPage from '../views/Admin/AOrderPage'
import AOrderSearchPage from '../views/Admin/AOrderSearchPage'
import AOrderDetailPage from '../views/Admin/AOrderDetailPage'
import AEventPage from '../views/Admin/AEventPage'
import AEventSearchPage from '../views/Admin/AEventSearchPage'
import AEventDetailPage from '../views/Admin/AEventDetailPage'
import AEventEditPage from '../views/Admin/AEventEditPage'
import AEventRegisterPage from '../views/Admin/AEventRegisterPage'

//테스트
import CategoryTest from '../views/Test/CategoryTest'
import PortoneTest from '../views/Test/PortoneTest'


const routes = [
  {
    path: '/',
    name: 'mainpage',
    component: MainPage
  },
  {
    path: '/search',
    name: 'searchpage',
    component: SearchPage
  },
  {
    path: '/book',
    name: 'book',
    component: BookDetailPage
  },
  {
    path: '/cart',
    name: 'cart',
    component: CartPage
  },
  {
    path: '/order',
    name: 'order',
    component: OrderPage
  },
  {
    path: '/order-result',
    name: 'orderresult',
    component: OrderResultPage
  },
  {
    path: '/bestseller',
    name: 'bestseller',
    component: BestsellerPage
  },
  {
    path: '/new',
    name: 'new',
    component: NewBookPage
  },
  {
    path: '/category',
    name: 'category',
    component: CategoryPage
  },
  {
    path: '/editorpick',
    name: 'editorpick',
    component: EditorPickPage
  },
  {
    path: '/event',
    name: 'event',
    component: EventPage
  },
  {
    path: '/event/detail',
    name: 'event/detail',
    component: EventDetailPage
  },
  {
    path: '/cs',
    name: 'cs',
    component: CSPage
  },
  {
    path: '/cs/register',
    name: 'csregister',
    component: CSRegisterPage
  },
  {
    path: '/faq',
    name: 'faq',
    component: FAQPage
  },
  {
    path: '/notice',
    name: 'notice',
    component: NoticePage
  },
  {
    path: '/notice/detail',
    name: 'noticedetail',
    component: NoticeDetailPage
  },
  {
    path: '/admin/login',
    name: 'adminlogin',
    component: ALoginPage
  },
  {
    path: '/admin/book',
    name: 'adminbook',
    component: ABookPage
  },
  {
    path: '/admin/book/search',
    name: 'adminbooksearch',
    component: ABookSearchPage
  },
  {
    path: '/admin/book/detail',
    name: 'adminbookdetail',
    component: ABookDetailPage
  },
  {
    path: '/admin/book/register',
    name: 'adminbookregister',
    component: ABookRegisterPage
  },
  {
    path: '/admin/book/edit',
    name: 'adminbookedit',
    component: ABookEditPage
  },
  {
    path: '/admin/order',
    name: 'adminorder',
    component: AOrderPage
  },
  {
    path: '/admin/order/search',
    name: 'adminordersearch',
    component: AOrderSearchPage
  },
  {
    path: '/admin/order/detail',
    name: 'adminorderdetail',
    component: AOrderDetailPage
  },
  {
    path: '/admin/event',
    name: 'adminevent',
    component: AEventPage
  },
  {
    path: '/admin/event/search',
    name: 'admineventsearch',
    component: AEventSearchPage
  },
  {
    path: '/admin/event/detail',
    name: 'admineventdetail',
    component: AEventDetailPage
  },
  {
    path: '/admin/event/edit',
    name: 'admineventedit',
    component: AEventEditPage
  },
  {
    path: '/admin/event/register',
    name: 'admineventregister',
    component: AEventRegisterPage
  },




  {
    path: '/categorytest',
    name: 'categoryTest',
    component: CategoryTest
  },
  {
    path: '/portone',
    name: 'Portone',
    component: PortoneTest
  },



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
