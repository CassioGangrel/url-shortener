export async function shortenURL(url: URL): Promise<string> {
  const requestConfig = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      url: url.toString(),
    }),
  };

  const response = await fetch("/shorten-url", requestConfig);
  const responseData = await response.json();
  return responseData.url;
}
