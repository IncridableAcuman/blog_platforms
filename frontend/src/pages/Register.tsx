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
import { registerSchema } from "@/lib/validation"
import { useAuthStore } from "@/stores/authStore"
import { toast } from "sonner"
import axiosInstance from "@/api/axiosInstance"
import { useEffect } from "react"


function Register() {
  const form = useForm<z.infer<typeof registerSchema>>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      username: "",
      email:"",
      password:""
    },
  })
  const navigate=useNavigate();
  const login=useAuthStore(state=>state.login);

  const handleSubmit=async ()=>{
    try {
      const {data}=await axiosInstance.post("/auth/register",form.getValues());
      const user = {
        id: data.id,
        username: data.username,
        email: data.email,
        role: data.role,
      };
      login(user,data.accessToken);
      localStorage.setItem("accessToken",data.accessToken);
      localStorage.setItem("role",user.role);
      toast.success("Successfully");
      navigate("/");
    } catch (error) {
      console.log(error);
      toast.error("Something went wrong!");
    }
  }

  useEffect(() => {
    if(localStorage.getItem("accessToken")){
      navigate("/");
    }
  }, [navigate]);

  return (
    <div className="w-full h-screen flex items-center justify-center">
      <div className="w-full max-w-md shadow shadow-gray-100 p-6 rounded-md">
        <h1 className="text-2xl font-bold pb-3 text-center">Sign Up</h1>
        <Form {...form }>
      <form  className="space-y-8" onSubmit={form.handleSubmit(handleSubmit)}>
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
