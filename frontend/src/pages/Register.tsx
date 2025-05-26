import { z } from "zod"
import { zodResolver } from '@hookform/resolvers/zod'
import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { useNavigate } from "react-router-dom"

const formSchema = z.object({
  username: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
  email:z.string().email({message:"Your input type email forexample johndoe@gmail.com"}),
  password:z.string().min(8,{message:"Password must be at least min 8 characters."})
  .max(50,{message:"Password must be at least max 50  characters."})
})

function Register() {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      email:"",
      password:""
    },
  })
  const navigate=useNavigate();

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values)
  }

  return (
    <div className="w-full h-screen flex items-center justify-center">
      <div className="w-full max-w-md shadow shadow-gray-100 p-6 rounded-md">
        <h1 className="text-2xl font-bold pb-3 text-center">Sign Up</h1>
        <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input placeholder="johndoe" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" placeholder="johndoe@gmail.com" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Password</FormLabel>
              <FormControl>
                <Input type="password" placeholder="********" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" className="w-full shadow cursor-pointer">Submit</Button>
      </form>
            <p className="pt-3 text-sm">Already have an account?{" "}<span className="font-semibold underline cursor-pointer hover:text-gray-300 transition duration-300"
             onClick={()=>navigate("/login")}>Sign In</span></p>
    </Form>
      </div>
    </div>
    
  )
}

export default Register
