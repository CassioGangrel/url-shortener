import { shortenURL } from "@/commands/shorten-url";
import React from "react";
import styled from "styled-components";

const baseStyle = `
font-size: 1.5em;
text-align: center;
color: palevioletred;
text-decoration: none;
`;

const Select = styled.select`
  ${baseStyle}
`;

const Anchor = styled.a`
  ${baseStyle}
`;

const StyledInput = styled.input`
  ${baseStyle}
`;

const StyledButton = styled.button`
  ${baseStyle}
`;

export const ShortemUrl: React.FC = () => {
  const [shortedUrl, setShortedUrl] = React.useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const urlInput = event.currentTarget.elements.namedItem("url");
    const protocolInput = event.currentTarget.elements.namedItem("protocol");

    if (
      urlInput instanceof HTMLInputElement &&
      protocolInput instanceof HTMLSelectElement
    ) {
      const url = new URL(protocolInput.value + urlInput.value);

      const shorten = await shortenURL(url);
      setShortedUrl(shorten);
    }
  };

  return shortedUrl ? (
    <Anchor href={shortedUrl}>{shortedUrl}</Anchor>
  ) : (
    <p>Hola World</p>
  );
};
