import {useEffect, useState} from "react";
import axios from "axios";

const PageJS = () => {
  const [page, setPage] = useState(1);
  const [data, setData] = useState();

  useEffect(() => {
    const getData = async () => {
      const response = await axios.get(`https://jsonplaceholder.typicode.com/todos/${page}`);
      setData(response.data);
    }

    getData();
  }, [page])

  if (!data) return <h1>Loading...</h1>

  return <div>
    <h1>Page (JS version)</h1>
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