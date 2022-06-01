import { ShortemUrl } from '@/components/input';
import styled from 'styled-components';
import './App.css';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  
  height: 100vh;
  width: 100vw;

  background-color: #f5f5f5;
`

function App() {
  return (
    <Container className="App">
      <ShortemUrl></ShortemUrl>
    </Container>
  );
}

export default App;
