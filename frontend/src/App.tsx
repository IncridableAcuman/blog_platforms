import { Route, Routes } from 'react-router-dom'
import Home from './pages/Home'
import ProfileForm from './pages/ProfileForm'
import Register from './pages/Register'
import {Toaster} from '@/components/ui/sonner'
const App = () => {
  return (
    <>
    <Toaster/>
    <Routes >
      <Route path='/' element={<Home/>} />
      <Route path='/login' element={<ProfileForm/>} />
      <Route path='/register' element={<Register/>} />
    </Routes>
    </>
  )
}

export default App