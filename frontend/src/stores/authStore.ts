import {create} from 'zustand';

interface User{
    id:number;
    username:string;
    email:string;
    role:string;
}
interface AuthState{
    user:User | null;
    token:string | null;
    isAuthenticated:boolean;
    login:(user:User,token:string)=>void;
    logout:()=>void;
    setToken:(token:string)=>void;
}

export const useAuthStore=create<AuthState>((set)=>({
    user:null,
    token:null,
    isAuthenticated:false,

    login:(user,token)=>
        set(()=>({
            user,
            token,
            isAuthenticated:true
        })),

    logout:()=>
        set(()=>({
            user:null,
            token:null,
            isAuthenticated:false
        })),
    
    setToken:(token)=>
        set(()=>({
            token,
            isAuthenticated:!!token
        })),
}));