import React from 'react';
import { Box, Typography, Button } from '@mui/material';
import { styled } from '@mui/system';

const NotFoundPageWrapper = styled(Box)(() => ({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  height: '100vh',
  backgroundImage: 'url("/image.png")', // Use your background image here
  backgroundSize: 'cover',
  backgroundPosition: 'center',
  backgroundRepeat: 'no-repeat',
  textAlign: 'center',
  padding: '0 20px',
  color: '#fff',
}));

const Title = styled(Typography)(() => ({
  fontSize: '50px',
  fontWeight: '700',
  color: '#e74c3c',
  marginBottom: '20px',
}));

const Description = styled(Typography)(() => ({
  fontSize: '20px',
  color: '#fff',
  marginBottom: '30px',
}));

const StyledButton = styled(Button)(() => ({
  backgroundColor: '#8e44ad',
  color: '#fff',
  padding: '15px 40px',
  fontSize: '18px',
  borderRadius: '5px',
  boxShadow: '0 5px 15px rgba(0, 0, 0, 0.1)',
  '&:hover': {
    backgroundColor: '#732d91',
    boxShadow: '0 8px 25px rgba(0, 0, 0, 0.15)',
  },
}));

const Logo = styled('img')({
  width: '100px',
  marginBottom: '20px',
});

const NotFoundPage = () => {
  return (
    <NotFoundPageWrapper>
      <div>
        <Logo src="/logo.svg" alt="TradeClover Logo" />
        <Title>404 - Page Not Found</Title>
        <Description>
          Oops! The page you are looking for does not exist.
        </Description>
        <StyledButton variant="contained" onClick={() => window.location.href = '/'}>
          Go to Home
        </StyledButton>
      </div>
    </NotFoundPageWrapper>
  );
};

export default NotFoundPage;
