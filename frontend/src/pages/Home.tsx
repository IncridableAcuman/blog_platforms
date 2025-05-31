import Cards from "@/components/Cards"
import Navbar from "@/components/Navbar"
import { useEffect } from "react"
import { useNavigate } from "react-router-dom"

const Home = () => {
  const navigate=useNavigate();
  useEffect(()=>{
    if(!localStorage.getItem("accessToken")){
      navigate("/login")
    }
  },[navigate]);
  return (
    <>
    <Navbar/>
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 pt-24">
      <Cards/>      
    </div>
    </>
  )
}

export default Home