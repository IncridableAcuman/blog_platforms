import { useEffect, useState } from "react"
import axiosInstance from "@/api/axiosInstance"
import { toast } from "sonner"
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
import { DialogDemo } from "./DialogDemo"
import { AlertDialogDemo } from "./AlertDialogDemo"
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
  DialogClose,
} from "@/components/ui/dialog"

const Cards = () => {
  const [posts, setPosts] = useState([])
  const [open, setOpen] = useState(false)
  const [selectedPost, setSelectedPost] = useState(null)

  const role = localStorage.getItem("role")
  const token = localStorage.getItem("accessToken")

  const handleSubmit = async () => {
    try {
      const { data } = await axiosInstance.get("/post/get/all", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        withCredentials: true,
      })
      setPosts(data)
    } catch (error) {
      console.log(error)
      toast.error("Something went wrong!")
    }
  }

  const handleDelete = (id) => {
    setPosts((prevPosts) => prevPosts.filter((post) => post.id !== id))
  }

  useEffect(() => {
    handleSubmit()
  }, [])

  const handleReadMore = (post) => {
    setSelectedPost(post)
    setOpen(true)
  }

  return (
    <>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 p-4">
        {posts.map((item) => (
          <Card className="w-full" key={item?.id}>
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
                    <AccordionContent>{item?.content?.slice(0, 20)}...</AccordionContent>
                  </AccordionItem>
                </Accordion>
              </div>
            </CardContent>
            <CardFooter className="flex justify-between items-center">
              <Button variant="outline" onClick={() => handleReadMore(item)}>
                Read more
              </Button>
              {role === "ADMIN" && (
                <>
                  <DialogDemo id={item?.id} />
                  <AlertDialogDemo id={item?.id} onDelete={handleDelete} />
                </>
              )}
              <p className="text-sm text-muted-foreground">{item?.author}</p>
            </CardFooter>
          </Card>
        ))}
      </div>

      {/* Dialog faqat bitta va tanlangan postga bog‘langan */}
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent>
          {selectedPost && (
            <>
              <DialogHeader>
                <DialogTitle>{selectedPost.title}</DialogTitle>
                <DialogDescription>
                  {selectedPost.content}
                </DialogDescription>
              </DialogHeader>
              <div className="py-4">
                <img
                  src={selectedPost.image}
                  alt={selectedPost.title}
                  className="w-full h-48 object-cover rounded-md mb-4"
                />
                <p className="text-muted-foreground">
                  Author: {selectedPost.author}
                </p>
              </div>
              <DialogFooter>
                <DialogClose asChild>
                  <Button variant="outline">Close</Button>
                </DialogClose>
              </DialogFooter>
            </>
          )}
        </DialogContent>
      </Dialog>
    </>
  )
}

export default Cards