import type { Post } from '@/interfaces/Post'
import {create} from 'zustand'

interface PostState{
    post:Post;
    setPost:(data:Partial<Post>)=>void;
    resetPost:()=>void;
}

const initialPost:Post={
    title:'',
    content:'',
    author:'',
    image:null,
    price:0,
    sourceUrl:''
}
export const usePostStore=create<PostState>((set)=>({
post:initialPost,
setPost:(data)=>
    set((state)=>({
        post:{...state.post,...data},
    })),
    resetPost:()=>set({post:initialPost})
}));