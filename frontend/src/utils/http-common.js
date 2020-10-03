import axios from 'axios'
import store from '../store/index.js'

export default axios.create({
  baseURL: 'http://j3a510.p.ssafy.io:8888/',
  headers: {
    'Content-type': 'application/json',
    // 'X-AUTH-TOKEN':
    //   'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNDg1MTE1NjczIiwiaWF0IjoxNjAxNjk3NjExLCJleHAiOjE2MDE3MDQ4MTF9.vCPV7rbxQvK0-OaTz-wOKJziAZE7EFCayAKgGJiB2NM',
  },
})
