import axios from 'axios'
import store from '../store/index.js'

export default axios.create({
  baseURL: 'http://j3a510.p.ssafy.io:8888/',
  //   headers: {
  //     'Content-type': 'application/json',
  //     Authorization: store.state.token,
  //   },
})
