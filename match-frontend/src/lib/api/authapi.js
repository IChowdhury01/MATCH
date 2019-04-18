import axios from 'axios';

export const Register = ({email, username, password}) => axios.post('', { email, username, password });
export const Login = ({username, userpassword}) => axios.post('', { username, userpassword });
