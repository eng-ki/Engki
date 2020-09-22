import axios from 'axios';
import store from '../store';

export default axios.create({
    baseURL: 'http://i3a402.p.ssafy.io:8090/devlog/api',
    // baseURL: 'http://localhost:8090/devlog/api',
    headers: {
        'Content-type': 'application/json',
        Authorization : store.state.token,
    },
})