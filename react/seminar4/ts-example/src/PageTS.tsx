import {useEffect, useState} from "react";
import axios from "axios";
import './Page.scss';

type TData = {
  completed: boolean;
  id: number;
  title: string;
  userId: number;
}

const PageJS = () => {
  const [page, setPage] = useState<number>(1);
  const [data, setData] = useState<TData>();

  useEffect(() => {
    const getData = async () => {
      const response = await axios.get<TData>(`https://jsonplaceholder.typicode.com/todos/${page}`);
      setData(response.data);
    }

    getData();
  }, [page])

  if (!data) return <h1>Loading...</h1>

  return <div className="wrapper">
    <h1>Page (TS version)</h1>
    <div>
      <p>{data.completed}</p>
      <p>{data.id}</p>
      <p>{data.title}</p>
      <p>{data.userId}</p>
    </div>
    <button onClick={() => setPage(page - 1)}>prev page</button>
    <button onClick={() => setPage(page + 1)}>next page</button>
  </div>
}

export default PageJS;