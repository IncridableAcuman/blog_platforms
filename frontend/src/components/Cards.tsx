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
const Cards = () => {
  return (
    <>
    <div className="flex items-center justify-center">
 <Card className="w-[350px]">
      <CardHeader>
        <img src="./webpack.png" alt="webpack" />
      </CardHeader>
      <CardContent>
        <form>
          <div className="grid w-full items-center gap-4">
            <div className="flex items-center justify-between px-2">
                <h1>Webpack Course</h1>
                <p>99.9$</p>
            </div>
            <Accordion type="single" collapsible className="w-full">
            <AccordionItem value="item-1">
                <AccordionTrigger>What is Webpack?</AccordionTrigger>
                <AccordionContent>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus, aliquam.
                </AccordionContent>
            </AccordionItem>
        </Accordion>
          </div>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button variant="outline">Read more</Button>
        <p className="text-sm">John Doe</p>
      </CardFooter>
    </Card>        
    </div>
     
    </>
  )
}

export default Cards