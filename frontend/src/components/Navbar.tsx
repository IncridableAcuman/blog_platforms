import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet"
import { Textarea } from "./ui/textarea"
import { Facebook, Github, Instagram, Linkedin, Twitter } from "lucide-react"
import React, { useState } from "react"
import { DropdownMenu } from "./ui/dropdown-menu"
import { toast } from "sonner"
import axiosInstance from "@/api/axiosInstance"
import { useNavigate } from "react-router-dom"

const Navbar = () => {
 const role=localStorage.getItem("role");
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    author: "",
    image:'',
    price: 0,
    sourceUrl: "",
  })

  const navigate = useNavigate()

  const handleLogout = async () => {
    try {
      await axiosInstance.post("/auth/logout")
      localStorage.removeItem("accessToken")
      localStorage.removeItem("role")
      toast.success("Successfully logged out")
      navigate("/login")
    } catch (error) {
      console.error(error)
      toast.error("Something went wrong!")
    }
  }


  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: name === "price" ? Number(value) : value,
    }))
  }

  const handleCreatePost = async () => {
    try {
    const {data}=await axiosInstance.post("/post/create",formData);
    setFormData(data);
    toast.success("Post created");
    } catch (error) {
      console.log(error)
      toast.error("Something went wrong");
    }
    
  }

  const renderForm = () => (
    <div className="grid gap-4 py-4 p-4">
      {[
        { label: "Title", name: "title", type: "text", value: formData.title },
        { label: "Author", name: "author", type: "text", value: formData.author },
        { label: "Price", name: "price", type: "number", value: formData.price },
        { label: "Source URL", name: "sourceUrl", type: "text", value: formData.sourceUrl },
        { label: "Image", name: "image", type: "text", value: formData.image },
      ].map(({ label, name, type, value }) => (
        <div key={name} className="grid grid-cols-4 items-center gap-4">
          <Label htmlFor={name} className="text-right">{label}</Label>
          <Input
            id={name}
            name={name}
            type={type}
            value={value}
            onChange={handleInputChange}
            className="col-span-3"
            placeholder={label}
          />
        </div>
      ))}
      <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="content" className="text-right">Content</Label>
        <Textarea
          id="content"
          name="content"
          placeholder="Content"
          value={formData.content}
          onChange={handleInputChange}
          className="col-span-3"
        />
      </div>
      {/* <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="image" className="text-right">Image</Label>
        <Input
          id="image"
          type="text"
          value={formData.image}
          onChange={handleInputChange}
          className="col-span-3"
        />
      </div> */}
    </div>
  )

  const renderFooter = () => (
    <SheetFooter className="flex flex-col gap-2">
      <SheetClose asChild>
        <Button onClick={handleCreatePost}>Create Post</Button>
      </SheetClose>
      <p className="text-sm text-center text-gray-500">Lorem ipsum dolor sit amet, consectetur.</p>
      <div className="flex justify-center gap-4">
        <Github size={18} />
        <Instagram size={18} />
        <Linkedin size={18} />
        <Facebook size={18} />
        <Twitter size={18} />
      </div>
    </SheetFooter>
  )

  return (
    <header className="fixed top-0 left-0 w-full bg-gray-800 text-white px-6 py-4 z-50 flex justify-between items-center">
      <a href="/" className="text-xl font-semibold">Blog.</a>
      <div className="flex items-center gap-4">
        <Sheet>
          <SheetTrigger asChild>
            {role==="ADMIN" && (
            <Button variant="outline">Create</Button>              
            )}
          </SheetTrigger>
          <SheetContent>
            <SheetHeader>
              <SheetTitle>Posts</SheetTitle>
            </SheetHeader>
            {renderForm()}
            {renderFooter()}
          </SheetContent>
        </Sheet>

        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Avatar className="cursor-pointer">
              <AvatarImage src="https://github.com/shadcn.png" />
              <AvatarFallback>AI</AvatarFallback>
            </Avatar>
          </DropdownMenuTrigger>
          <DropdownMenuContent className="w-56">
            <DropdownMenuLabel>My Account</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem>GitHub</DropdownMenuItem>
            <DropdownMenuItem>Support</DropdownMenuItem>
            <DropdownMenuItem disabled>API</DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem onClick={handleLogout}>
              Log out
              <DropdownMenuShortcut>⇧⌘Q</DropdownMenuShortcut>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
  )
}

export default Navbar
