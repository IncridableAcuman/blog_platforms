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
import { useAuthStore } from "@/stores/authStore"
import { toast } from "sonner"
import axiosInstance from "@/api/axiosInstance"
import { loginSchema } from "@/lib/validation"
import { useEffect } from "react"


function ProfileForm() {
  const form = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email:"",
      password:""
    },
  })
  const navigate=useNavigate();

  const login=useAuthStore((state)=>state.login);

  const handleSubmit=async ()=>{
    try {
      const {data}=await axiosInstance.post("/auth/login",form.getValues());
      const user = {
        id: data.id,
        username: data.username,
        email: data.email,
        role: data.role,
      };
      localStorage.setItem("accessToken",data?.accessToken);
      localStorage.setItem("role",user.role);
      toast.success("Successfully");
      login(user,data.accessToken);
      console.log(data);
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
        <h1 className="text-2xl font-bold pb-3 text-center">Sign In</h1>
        <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-8">
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
      <p className="pt-3 text-sm">Don't have an account?{" "}<span className="font-semibold underline cursor-pointer hover:text-gray-300 transition duration-300"
       onClick={()=>navigate("/register")}>Sign Up</span></p>
    </Form>
      </div>
    </div>
    
  )
}

export default ProfileForm
