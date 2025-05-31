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
    <div className="pt-24">
      <Cards/>      
    </div>
    </>
  )
}

export default Home