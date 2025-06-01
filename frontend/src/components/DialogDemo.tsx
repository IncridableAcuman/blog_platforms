import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useState } from "react"
import { Textarea } from "./ui/textarea"

export function DialogDemo() {
      const [formData, setFormData] = useState({
        title: "",
        content: "",
        author: "",
        image:'',
        price: 0,
        sourceUrl: "",
      })

      const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
          const { name, value } = e.target
          setFormData(prev => ({
            ...prev,
            [name]: name === "price" ? Number(value) : value,
          }))
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

    </div>
  )

  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          <Button variant="outline">Edit</Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>Edit post</DialogTitle>
          </DialogHeader>
          <div className="grid gap-4">
            {renderForm()}
          </div>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline">Cancel</Button>
            </DialogClose>
            <Button type="submit">Save changes</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  )
}
