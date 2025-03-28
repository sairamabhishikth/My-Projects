import React from 'react';
import { Box, Typography, Button } from '@mui/material';
import { styled } from '@mui/system';

const MaintenancePageWrapper = styled(Box)({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  height: '100vh',
  backgroundImage: 'url("/bcimage.png")', // Make sure the image is in the public folder
  backgroundSize: 'cover',
  backgroundPosition: 'center',
  backgroundRepeat: 'no-repeat',
  textAlign: 'center',
  padding: '0 20px',
  color: '#fff',
});

const Title = styled(Typography)({
  fontSize: '40px',
  fontWeight: '700',
  color: '#e74c3c',
  marginBottom: '20px',
});

const Description = styled(Typography)({
  fontSize: '20px',
  color: '#fff',
  marginBottom: '30px',
});

const StyledButton = styled(Button)({
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
});

const Logo = styled('img')({
  width: '100px',
  marginBottom: '20px',
});

const MaintenancePage = () => {
  return (
    <MaintenancePageWrapper>
      <div>
        <Logo src="/logo.svg" alt="TradeClover Logo" />
        <Title>TradeClover - Under Maintenance</Title>
        <Description>
          We are currently working on some updates. Please check back later.
        </Description>
        <StyledButton variant="contained">Contact Support</StyledButton>
      </div>
    </MaintenancePageWrapper>
  );
};

export default MaintenancePage;
