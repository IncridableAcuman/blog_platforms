import Cards from "@/components/Cards"
import Navbar from "@/components/Navbar"

const Home = () => {
  return (
    <>
    <Navbar/>
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 pt-24">
      <Cards/>
      <Cards/> 
      <Cards/> 
      <Cards/>       
    </div>
    </>
  )
}

export default Home