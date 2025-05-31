import {z} from 'zod';
// login schema
export const loginSchema=z.object({
    email:z.string({message:"Email is wrong or is empty"}).email(),
    password:z.string({message:"Password must be between 8 and 1024 characters"}).min(8).max(1024)
});
// register schema
export const registerSchema=z.object({
    username:z.string({message:"Username must be between 3 and 50 characters"}).min(3).max(50),
    email:z.string({message:"Email is wrong or is empty"}).email(),
    password:z.string({message:"Password must be between 8 and 1024 characters"}).min(8).max(1024)
});

// post schema
export const postSchema=z.object({
    title:z.string({message:"Title must be between 3 and 100 characters!"}).min(3).max(100),
    content:z.string({message:"Conent must be between 5 and 3000 characters!"}).min(5).max(300),
    author:z.string({message:"Author must be between 3 and 50 characters!"}).min(3).max(50),
    image:z.any().refine(file=>file instanceof File,{
        message:'Image is required and must be a valid file!'
    }),
    price:z.number({message:"Price must be required!"}),
    sourceUrl:z.string({message:"Url must be required!"})
});