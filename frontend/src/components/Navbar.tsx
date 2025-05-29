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
import { useState } from "react"
import { DropdownMenu } from "./ui/dropdown-menu"
import { toast } from "sonner"
import axiosInstance from "@/api/axiosInstance"
import { useNavigate } from "react-router-dom"

const Navbar = () => {
  const [isOpen,setIsOpen]=useState(false);
  const navigate=useNavigate();
  const handleLogout=async ()=>{
    try {
      await axiosInstance.post("/auth/logout");
      localStorage.removeItem("accessToken");
      localStorage.removeItem("role");
      toast.success("Successfully logged out");
      navigate("/login");
    } catch (error) {
      console.log(error);
      toast.error("Something went wrong!");
    }
  }

  return (
    <>
    <div className="fixed top-0 left-0 w-full bg-gray-800 text-white flex items-center justify-between px-4 sm:px-6 md:px-8 lg:px-10 py-4 z-50">
        <a href="/" className="text-xl font-semibold">Blog.</a>
        <div className="flex items-center gap-5">
        <Sheet>
            <SheetTrigger asChild>
        <Button variant="outline" className="rounded-md shadow cursor-pointer">Create</Button>
      </SheetTrigger>
      <SheetContent>
        <SheetHeader>
          <SheetTitle>Create post</SheetTitle>
        </SheetHeader>
        <div className="grid gap-4 py-4 p-4">
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="title" className="text-right">
              Title
            </Label> 
            <Input type="text" placeholder="Title" className="col-span-3 w-full" />
          </div>
          {/* title */}
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="content" className="text-right">
              Content
            </Label> 
            <Textarea id="content" placeholder="Content"  className="col-span-3 w-full" />
          </div>
          {/* content */}
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="author" className="text-right">
              Author
            </Label> 
            <Input type="text" placeholder="Author" className="col-span-3 w-full" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="image" className="text-right">
              Image
            </Label> 
            <Input type="file" placeholder="Image" className="col-span-3 w-full" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="price" className="text-right">
              Price
            </Label> 
            <Input type="number" placeholder="Price" className="col-span-3 w-full" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 w-full">
            <Label htmlFor="sourceUrl" className="text-right">
              SourceUrl
            </Label> 
            <Input type="text" placeholder="Source URL" className="col-span-3 w-full" />
          </div>
        </div>
        <SheetFooter>
          <SheetClose asChild>
            <Button type="submit">Create Post</Button>
          </SheetClose>
            <p className="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Placeat, minus?</p>
            <div className="flex items-center justify-between p-3">
                <Github size={18}/>
                <Instagram size={18}/>
                <Linkedin size={18}/>
                <Facebook size={18}/>
                <Twitter size={18} />
            </div>
        </SheetFooter>
      </SheetContent>
    </Sheet>
    <div className="">
      <DropdownMenu>
            <DropdownMenuTrigger asChild>
            <Avatar className="cursor-pointer relative" onClick={()=>setIsOpen(!isOpen)}>
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
        </div>
    </div>
    </>
  )
}

export default Navbar