import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card"
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"
import { useEffect, useState } from "react"
import { toast } from "sonner"
import axiosInstance from "@/api/axiosInstance"
import { DialogDemo } from "./DialogDemo"
import { AlertDialogDemo } from "./AlertDialogDemo"

const Cards = () => {
  const [posts, setPosts] = useState([])
  const role=localStorage.getItem("role");

  const handleSubmit = async () => {
    try {
      const { data } = await axiosInstance.get("/post/get/all")
      setPosts(data)
    } catch (error) {
      console.log(error)
      toast.error("Something went wrong!")
    }
  }

  useEffect(() => {
    handleSubmit()
  }, [])

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 p-4">
      {posts.map((item, index) => (
        <Card className="w-full" key={index}>
          <CardHeader>
            <img
              src={item?.image}
              alt={item?.title}
              className="w-full h-40 object-cover rounded-md"
            />
          </CardHeader>
          <CardContent>
            <div className="grid w-full items-center gap-4">
              <div className="flex items-center justify-between px-2">
                <h1 className="text-lg font-semibold">{item?.title}</h1>
                <p className="text-muted-foreground">{item?.price}$</p>
              </div>
              <Accordion type="single" collapsible className="w-full">
                <AccordionItem value="item-1">
                  <AccordionTrigger>What is {item?.title}?</AccordionTrigger>
                  <AccordionContent>{item?.content.slice(0,20)}...</AccordionContent>
                </AccordionItem>
              </Accordion>
            </div>
          </CardContent>
          <CardFooter className="flex justify-between">
            <Button variant="outline">Read more</Button>
            {role==="ADMIN" && (<Button variant="outline"><DialogDemo/></Button>)}
            {role==="ADMIN" && (<Button variant="outline"><AlertDialogDemo/></Button> )}                
            <p className="text-sm text-muted-foreground">{item?.author}</p>
          </CardFooter>
        </Card>
      ))}
    </div>
  )
}

export default Cards
