# Multi-stage build: build Angular (SSR) then run with Node
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
# Build browser and server bundles for Angular Universal (skip prerendering)
RUN npx ng build --configuration=production

FROM node:18-alpine AS runner
WORKDIR /app
ENV PORT=4000
COPY --from=builder /app/dist/expense-front /app/dist/expense-front
EXPOSE 4000
# Run the server bundle produced by the SSR build
CMD ["node", "dist/expense-front/server/server.mjs"]
